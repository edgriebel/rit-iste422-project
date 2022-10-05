{ pkgs }: {
    deps = [
        pkgs.gradle_6
        pkgs.unzip
        pkgs.wget
        pkgs.openssh_with_kerberos
        pkgs.graalvm17-ce
        pkgs.maven
        pkgs.replitPackages.jdt-language-server
        pkgs.replitPackages.java-debug
    ];
}