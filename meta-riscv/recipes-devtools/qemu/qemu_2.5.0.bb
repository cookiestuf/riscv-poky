require recipes-devtools/qemu/qemu.inc

SRC_URI = "git://github.com/riscv/riscv-qemu.git;branch=riscv-all;destsuffix=${S}"
SRCREV_pn-qemu-native = "4c510a3f5ea14af6fc136cdce71f84aa433d2a73"
SRCREV_pn-nativesdk-qemu = "4c510a3f5ea14af6fc136cdce71f84aa433d2a73"

SRC_URI_remove_class-native = "\
    file://fix-libcap-header-issue-on-some-distro.patch \
    file://cpus.c-qemu_cpu_kick_thread_debugging.patch \
    "

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac"

QEMU_TARGETS = "riscv64"

EXTRA_OECONF_remove = "--disable-numa --disable-lzo --disable-opengl --disable-gnutls"

COMPATIBLE_HOST_class-target_mips64 = "null"

do_install() {
    export STRIP="true"
    autotools_do_install
    install -d ${D}${datadir}/qemu

    # Prevent QA warnings about installed ${localstatedir}/run
    if [ -d ${D}${localstatedir}/run ]; then rmdir ${D}${localstatedir}/run; fi
}
