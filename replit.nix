{ pkgs }: {
    deps = [
        pkgs.bashInteractive pkgs.adoptopenjdk-hotspot-bin-16 pkgs.ant pkgs.gradle
    ];
}