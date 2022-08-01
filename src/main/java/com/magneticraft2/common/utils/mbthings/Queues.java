package com.magneticraft2.common.utils.mbthings;

import com.magneticraft2.common.registry.OnModLoad;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class Queues {
    public static final WorkQueue serverThread;
    public static final WorkQueue clientThread;
    public static final WorkQueue offThread;

    @OnModLoad
    private static void onModLoad() {
    }

    static {
        WorkQueue serverThread1 = null;
        WorkQueue clientThread1 = null;
        int threads = Runtime.getRuntime().availableProcessors();
        threads = Math.max(1, threads - 1); // if possible, leave a core for the main server threads
        try {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                threads = Math.max(1, threads - 1); // if possible, leave a core for the main client thread too
                clientThread1 = new WorkQueue();
            }
            serverThread1 = new WorkQueue();
        }catch (NoClassDefFoundError ignored){
            // happens when forge isn't loaded
        }
        serverThread = serverThread1;
        clientThread = clientThread1;
        offThread = new WorkQueue();
        offThread.addProcessingThreads(threads, "Magneticraft2 OffThread Queue Worker Thread #");
    }
}
