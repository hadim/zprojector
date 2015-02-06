package org.micromanager.zprojector;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;

import ij.plugin.ZProjector;

import mmcorej.TaggedImage;
import org.json.JSONException;
import org.micromanager.acquisition.TaggedImageQueue;
import org.micromanager.api.DataProcessor;
import org.micromanager.utils.MDUtils;
import org.micromanager.utils.MMScriptException;
import org.micromanager.utils.ReportingUtils;
import org.micromanager.utils.ImageUtils;
import org.micromanager.MMStudio;

public class ZProjectorProcessor extends DataProcessor{

    boolean isFirstImage;
    ImagePlus projectedImage;
    ImagePlus tmpImage;

    int width;
    int height;
    int numSlices;
    int numChannels;
    String type;

    int projection_method;
    
    ZProjectorConfiguration config_;

    public ZProjectorProcessor(){
        isFirstImage = true;
        setProjectionMethod(ZProjector.MAX_METHOD);
    }

    @Override
    public void makeConfigurationGUI() {
    	if(config_ == null){
			config_ = new ZProjectorConfiguration(this);
			MMStudio.getInstance().addMMBackgroundListener(config_);
    	}
		config_.setVisible(true);
    }
    
    public void process() {

        // Disable the processor if live mode is ON
        if(!gui_.isLiveModeOn()){

            try {
                TaggedImage image = (TaggedImage) poll();
                if (image != TaggedImageQueue.POISON) {
                    try {
                        produce(proccessTaggedImage(image));

                    } catch (Exception ex) {
                        produce(image);
                        ReportingUtils.logError(ex);
                    }
                } else {
                    // Must produce Poison (sentinel) image to terminate tagged image pipeline
                    produce(image);
                }
            } catch (Exception ex) {
                    ReportingUtils.logError(ex);
            }
        }
    }

    public TaggedImage proccessTaggedImage(TaggedImage image) throws JSONException, MMScriptException {

        // Init ImagePlus
        if(isFirstImage){
            isFirstImage = false;
            initProjectionFrame(image);
        }

        feedTmpImage(image);

        // Update ImagePlus only if all z slices and channels
        // for the current timepoint are here
        int currentZ = MDUtils.getSliceIndex(image.tags);
        int currentC = MDUtils.getChannelIndex(image.tags);

        if(currentZ == numSlices - 1 && currentC == numChannels - 1){
        updateProjectionFrame(image);
        }

      return image;
    }

    public void initProjectionFrame(TaggedImage image) throws JSONException, MMScriptException{

        gui_.message("Init projection frame");

        width = MDUtils.getWidth(image.tags);
        height = MDUtils.getHeight(image.tags);

        numSlices = MDUtils.getNumSlices(image.tags);
        numChannels = MDUtils.getNumChannels(image.tags);

        type = MDUtils.getPixelType(image.tags);

        projectedImage = new ImagePlus();
        projectedImage.setOpenAsHyperStack(true);
        
        String title = MDUtils.getSummary(image.tags).getString("Prefix");
        projectedImage.setTitle("MAX_" + title);

        tmpImage = new ImagePlus();
    }

    public void updateProjectionFrame(TaggedImage image) throws MMScriptException{

        gui_.message("Update projection frame");

        ZProjector zproj = new ZProjector(tmpImage);
        zproj.setMethod(getProjectionMethod());
        zproj.setStartSlice(1);
        zproj.setStopSlice(10);
        zproj.doHyperStackProjection(true);
        ImagePlus projection = zproj.getProjection();

        ImageProcessor p = projection.getProcessor();

        ImageStack stack;
        if(projectedImage.getProcessor() == null){
            stack = new ImageStack(width, height);
        }
        else{
            stack = projectedImage.getStack();
        }

        stack.addSlice(p);
        projectedImage.setStack(stack);
        projectedImage.show();

        tmpImage = new ImagePlus();
    }

    public void feedTmpImage(TaggedImage image){

        ImageStack stack;
        if(tmpImage.getProcessor() == null){
            stack = new ImageStack(width, height);
        }
        else{
            stack = tmpImage.getStack();
        }

        ImageProcessor p = ImageUtils.makeProcessor(image);
        stack.addSlice(p);
        tmpImage.setStack(stack);
    }

    public void setProjectionMethod(int method){
        projection_method = method;
    }

    public int getProjectionMethod(){
        return projection_method;
    }

}
