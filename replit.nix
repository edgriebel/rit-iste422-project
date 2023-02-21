{ pkgs }: {
    deps = [
        pkgs.nano
        pkgs.vimHugeX
        pkgs.graalvm17-ce
        pkgs.maven
        pkgs.replitPackages.jdt-language-server
        pkgs.replitPackages.java-debug
    ];
}