# ZProjector

Micro-Manager plugin to Z project images in live during Multi-Dimensional Acquisition (MDA).

During MDA with Z slices, ZProjector can be used to display a second window on which images are Z projected.

## Install

- Download [ZProjector.jar](https://raw.githubusercontent.com/hadim/zprojector/master/ZProjector.jar). (compiled with OpenJDK 1.6).
- Put it in `mmplugins/` in Micro-Manager (MM) installation directory.
- Launch MM
- Select Plugins > On-The-Fly Processors > Configure Processors
- Add ZProjector plugin

Now you can start an MDA, a secondary window will display and show Z projected images.

Note that you can change projection type in plugin configuration window.

## Current limitations

- Does not work with multiple channels MDA (will be fixed soon)
- MM crash when configuration window is closed
- Make the configuration window prettiest

## Author

Hadrien Mary <hadrien.mary@gmail.com>

## License

MIT License. See [LICENSE](LICENSE).

