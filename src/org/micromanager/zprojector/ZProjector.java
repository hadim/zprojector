package org.micromanager.zprojector;

import org.micromanager.api.MMProcessorPlugin;

public class ZProjector implements MMProcessorPlugin {

   public static final String menuName = "ZProjector";
   public static final String tooltipDescription = "Z Project during acquisitions";

   public static Class<?> getProcessorClass() {
      return ZProjectorProcessor.class;
   }

   @Override
   public String getInfo () {
      return "ZProjector Plugin";
   }

   @Override
   public String getDescription() {
      return tooltipDescription;
   }

   @Override
   public String getVersion() {
      return "1.0";
   }

   @Override
   public String getCopyright() {
      return "Hadrien Mary, 2015";
   }
}
