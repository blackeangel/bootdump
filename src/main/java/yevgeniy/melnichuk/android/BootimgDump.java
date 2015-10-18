package yevgeniy.melnichuk.android;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootimgDump {
    private static final Logger LOGGER = LoggerFactory.getLogger(BootimgDump.class);

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            LOGGER.info("provide path to boot.img");
            System.exit(1);
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            LOGGER.info("can't find " + args[0]);
            System.exit(1);
        }

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");

            FileChannel channel = raf.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 8, 32);
            map.order(ByteOrder.LITTLE_ENDIAN);
            IntBuffer intBuffer = map.asIntBuffer();

            int kernelSize = intBuffer.get();
            LOGGER.info(String.format("kernel size:\t0x%08X(%d)", kernelSize, kernelSize));

            int kernelAddr = intBuffer.get();
            LOGGER.info(String.format("kernel addr:\t0x%08X", kernelAddr));

            int ramdiskSize = intBuffer.get();
            LOGGER.info(String.format("ramdisk size:\t0x%08X(%d)", ramdiskSize, ramdiskSize));

            int ramdiskAddr = intBuffer.get();
            LOGGER.info(String.format("ramdisk addr:\t0x%08X", ramdiskAddr));

            int secondSize = intBuffer.get();
            LOGGER.info(String.format("second size:\t0x%08X(%d)", secondSize, secondSize));

            int secondAddr = intBuffer.get();
            LOGGER.info(String.format("second addr:\t0x%08X", secondAddr));

            int tagsAddr = intBuffer.get();
            LOGGER.info(String.format("tags addr:\t0x%08X", tagsAddr));

            int pageSize = intBuffer.get();
            LOGGER.info(String.format("page size:\t%d", pageSize));

            byte[] board = new byte[16];
            raf.seek(48);
            raf.read(board);
            LOGGER.info(String.format("board:\t\t%s", new String(board)));

            byte[] cmdLine = new byte[512];
            raf.seek(64);
            raf.read(cmdLine);
            LOGGER.info(String.format("cmdLine:\t%s", new String(cmdLine)));
        } finally {
            if (raf != null) {
                raf.close();
            }
        }
    }
}
