# dump boot.im

prints informataion like size and offset of kernel and ramdisk from a boot.img

code is based around `boot_img_hdr` from `bootimg.h`

    struct boot_img_hdr
    {
        uint8_t magic[BOOT_MAGIC_SIZE];
    
        uint32_t kernel_size;
        uint32_t kernel_addr;
    
        uint32_t ramdisk_size;
        uint32_t ramdisk_addr;
    
        uint32_t second_size;
        uint32_t second_addr;
    
        uint32_t tags_addr;
        uint32_t page_size;
        uint32_t unused[2];
    
        uint8_t name[BOOT_NAME_SIZE];
    
        uint8_t cmdline[BOOT_ARGS_SIZE];
    
        uint32_t id[8];
    
        uint8_t extra_cmdline[BOOT_EXTRA_ARGS_SIZE];
    } __attribute__((packed));

