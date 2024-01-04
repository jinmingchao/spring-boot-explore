//package explore.springboot.documentation.utils;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.chinapost.cggl.business.product.mapper.ProductMapper;
//import com.chinapost.cggl.common.threadpool.StockDeductionQueueScannerThreadPool;
//import com.chinapost.cggl.common.threadpool.StockDeductionResultQueueScannerThreadPool;
//import com.chinapost.cggl.common.threadpool.StockDeductionThreadPool;
//import com.chinapost.cggl.common.threadpool.task.StockDeductionQueueScannerThreadPoolTask;
//import com.chinapost.cggl.common.threadpool.task.StockDeductionResultQueueScannerThreadPoolTask;
//import com.chinapost.cggl.mdm.dto.ProductDTO;
//import com.chinapost.cggl.mdm.vo.StockDeductionVO;
//import com.chinapost.cggl.util.cggl.TokenUtil;
//import com.chinapost.cggl.util.cggl.HttpUtil;
//import com.chinapost.cggl.util.jd.JSchConnectUtils;
//import com.jcraft.jsch.JSchException;
//
//import java.util.List;
//import java.util.concurrent.*;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//@Configuration
//@EnableScheduling
//public class StockDeductionTask implements ApplicationRunner {
//
//    private static final Logger log = LoggerFactory.getLogger(StockDeductionTask.class);
//
//    @Autowired
//    ProductMapper productMapper;
//
//    @Value("${stockDeduction.appKey}")
//    String appKey;
//
//    @Value("${stockDeduction.appSecret}")
//    String appSecret;
//
//    @Value("${stockDeduction.openApiUrl}")
//    String openApiUrl;
//
//    @Value("${stockDeduction.itemUsage}")
//    String itemUsage;
//
//    @Value("${stockDeduction.busType}")
//    String busType;
//
//    @Value("${stockDeduction.deductionFileDirectory}")
//    String deductionFileDirectory;
//
//    @Value("${stockDeduction.fileSystemHost}")
//    String fileSystemHost;
//
//    @Value("${stockDeduction.fileSystemPort}")
//    Integer fileSystemPort;
//
//    @Value("${stockDeduction.fileSystemUserName}")
//    String fileSystemUserName;
//
//    @Value("${stockDeduction.fileSystemPassWord}")
//    String fileSystemPassWord;
//
//    @Value("${stockDeduction.historyPath}")
//    String historyPath;
//
//    String fileName;
//
//    Long duration = 60L;
//
//    JSchConnectUtils sshConnection;
//
//    private String accessToken;
//
//    private HttpUtil httpUtil;
//
//    private boolean isSuccess;
//
//    private RejectedExecutionHandler handler;
//
//    private StockDeductionQueueScannerThreadPool stockDeductionQueueScannerThreadPool;
//
//    private StockDeductionResultQueueScannerThreadPool stockDeductionResultQueueScannerThreadPool;
//
//    private StockDeductionThreadPool stockDeductionThreadPool;
//
//    private Future<Boolean> result_1;
//
//    private Future<Boolean> result_2;
//
//    private ConcurrentLinkedQueue<StockDeductionVO> stockDeductionQueue = new ConcurrentLinkedQueue();
//
//    private BlockingQueue<Future<JSONObject>> stockDeductionResultQueue = new LinkedBlockingDeque<>(500);
//
//    public StockDeductionTask() {
//        isSuccess = true;
//        sshConnection = null;
//        handler = ((runnable, executor) -> {
//            StringBuilder info = new StringBuilder();
//            info.append("Task ");
//            info.append(runnable.toString());
//            info.append(" has been rejected by ");
//            info.append(executor.toString());
//            info.append(".");
//            throw new RejectedExecutionException(info.toString());
//        });
//    }
//
//    /**
//     * 每天凌晨2点, 关闭线程池
//     */
//    @Scheduled(cron = "0 0 2 * * ?")
////    @Scheduled(fixedDelay = 2 * 60 * 1000, initialDelay = 60 * 1000)   //测试用
//    public void stockDeductionTaskPoolTerminal() {
//        log.info("StockDeductionTaskPoolTerminal task start.");
//
//        if (null != stockDeductionQueueScannerThreadPool) {
//            closeThreadPool(stockDeductionQueueScannerThreadPool, result_1);
//        }
//
//        if (null != stockDeductionResultQueueScannerThreadPool) {
//            closeThreadPool(stockDeductionResultQueueScannerThreadPool, result_2);
//        }
//
//        if (null != stockDeductionThreadPool) {
//            closeThreadPool(stockDeductionThreadPool, null);
//        }
//
//
//        if (this.isSuccess) {
//            log.info("StockDeductionTask is succeed.");
//            moveDeductionFileToHistoryPath();
//        } else {
//            log.error("StockDeductionTask is failed.");
//        }
//
//        if (null != this.sshConnection) {
//            this.sshConnection.closeSession();
//        }
//
//        log.info("StockDeductionTaskPoolTerminal task end.");
//    }
//
//    private void closeThreadPool(ThreadPoolExecutor childThreadPool, Future<Boolean> result) {
//        try {
//            Class<? extends ThreadPoolExecutor> clazz = childThreadPool.getClass();
//            ThreadPoolExecutor cThreadPool = clazz.cast(childThreadPool);
//            String packageName = cThreadPool.getClass().getName();
//            String childThreadPoolName = packageName.substring(packageName.lastIndexOf(".") + 1);
//
//            if (!cThreadPool.isShutdown()) {
//                cThreadPool.shutdown();
//                log.info(childThreadPoolName + " invoked shutdown()");
//
//                if (!childThreadPool.awaitTermination(null == this.duration ? 60L : this.duration, TimeUnit.SECONDS)) {
//                    childThreadPool.shutdownNow();
//                    log.info(childThreadPoolName + " invoked shutdownNow()");
//                } else {
//                    log.info(childThreadPoolName + " stopped.");
//                }
//
//                if (result == null) {
//                    log.warn(childThreadPoolName + "'s result is null.");
//                    childThreadPool = null;
//                    return;
//                }
//
//                Boolean r1 = result.get();
//                if (!r1) {
//                    log.debug(childThreadPoolName + "'s result is false.");
//                    isSuccess = false;
//                } else {
//                    log.debug(childThreadPoolName + "'s result is true.");
//                }
//
//
//                childThreadPool = null;
//            } else {
//                log.warn(childThreadPoolName + " do nothing.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.toString());
//        }
//    }
//
//    /**
//     * 每天0时执行库存扣减定时任务
//     */
////    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 30 * 1000)   //测试用
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void stockDeductionTask() {
//
//        if (!initThreadPool()) {
//            log.error("线程池初始化失败");
//            return;
//        }
//
//        isSuccess = true;
//        StringBuilder errorMsg = new StringBuilder();
//
//        if (null == this.appKey) {
//            errorMsg.append("appKey");
//        } else if (null == this.appSecret) {
//            errorMsg.append("appSecret");
//        } else if (null == this.itemUsage) {
//            errorMsg.append("itemUsage");
//        } else if (null == this.busType) {
//            errorMsg.append("busiType");
//        } else if (null == this.openApiUrl) {
//            errorMsg.append("openApiUrl");
//        } else if (null == this.deductionFileDirectory) {
//            errorMsg.append("deductionFileDirectory");
//        } else if (null == this.fileSystemHost) {
//            errorMsg.append("fileSystemHost");
//        } else if (null == this.fileSystemPort) {
//            errorMsg.append("fileSystemPort");
//        } else if (null == this.fileSystemUserName) {
//            errorMsg.append("fileSystemUserName");
//        } else if (null == this.fileSystemPassWord) {
//            errorMsg.append("fileSystemPassWord");
//        } else if (null == this.historyPath) {
//            errorMsg.append("historyPath");
//        }
//
//        if (errorMsg.length() > 0) {
//            errorMsg.append(" not exist.");
//            log.error(errorMsg.toString());
//            log.debug("isSuccess is false - 1");
//            this.isSuccess = false;
//            return;
//        }
//
//
//        accessToken = TokenUtil.getInstance().ObtainAccessToken(this.appKey, this.appSecret, this.openApiUrl);
//        log.debug("ACCESS TOKEN: " + this.accessToken);
//        if (null == this.accessToken) {
//            log.error("access token not exist.");
//            log.debug("isSuccess is false - 2");
//            this.isSuccess = false;
//            return;
//        }
//
//        //启动库存扣减任务扫描结果线程池
//        StockDeductionResultQueueScannerThreadPoolTask task_1 = new StockDeductionResultQueueScannerThreadPoolTask(stockDeductionResultQueue);
//        result_1 = stockDeductionResultQueueScannerThreadPool.submit(task_1);
//
//        //启动库存扣减任务扫描线程池
//        StockDeductionQueueScannerThreadPoolTask task_2 = new StockDeductionQueueScannerThreadPoolTask(stockDeductionThreadPool, stockDeductionResultQueue, stockDeductionQueue, httpUtil, productMapper, openApiUrl, itemUsage, busType, accessToken);
//        result_2 = stockDeductionQueueScannerThreadPool.submit(task_2);
//
//        fetchStockDeduction();
//    }
//
//    private boolean initThreadPool() {
//        try {
//            stockDeductionQueueScannerThreadPool = new StockDeductionQueueScannerThreadPool(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), Executors.defaultThreadFactory(), this.handler);
//            stockDeductionResultQueueScannerThreadPool = new StockDeductionResultQueueScannerThreadPool(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), Executors.defaultThreadFactory(), this.handler);
//            stockDeductionThreadPool = new StockDeductionThreadPool(8, 8, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), Executors.defaultThreadFactory(), this.handler);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//
//    }
//
//    private void fetchStockDeduction() {
//        try {
//            this.sshConnection = new JSchConnectUtils(this.fileSystemHost, this.fileSystemPort, this.fileSystemUserName, this.fileSystemPassWord);
//            this.sshConnection.connect();
//        } catch (JSchException var1) {
//            var1.printStackTrace();
//            int cnt = 0;
//            for (int i = 0; i < 3; i++) {
//                try {
//                    log.error("SFTP连接异常, 重试次数: " + (i + 1));
//                    this.sshConnection.connect();
//                    break;
//                } catch (JSchException e) {
//                    e.printStackTrace();
//                    cnt++;
//                }
//            }
//            if (3 == cnt) {
//                log.error("SFTP连接异常");
//                log.debug("isSuccess is false - 3");
//                this.isSuccess = false;
//                return;
//            }
//        }
//        try {
//            if (null != this.sshConnection.getSession()) {
//                this.fileName = this.sshConnection.execCommandForQueryingDeductionFile("ls " + this.deductionFileDirectory);
//                if (null != this.fileName) {
//                    log.info("fileName: " + this.fileName);
//                    String deductionInfo = this.sshConnection.readFile(this.deductionFileDirectory + this.fileName);
//                    JSONArray deductionInfoArr = JSONArray.parseArray(deductionInfo);
//                    transformData(deductionInfoArr);
//                } else {
//                    throw new Exception("fileName is null.");
//                }
//            } else {
//                throw new Exception("SSH Session is null.");
//            }
//        } catch (Exception var2) {
//            log.debug("isSuccess is false - 4");
//            this.isSuccess = false;
//            var2.printStackTrace();
//
//        }
//    }
//
//    private void transformData(JSONArray deductionInfoArr) {
//        try {
//            for (int i = 0; i < deductionInfoArr.size(); i++) {
//                JSONObject info = deductionInfoArr.getJSONObject(i);
//                String wareHouseCode = info.getString("institution");
//                JSONArray details = info.getJSONArray("details");
//                for (int j = 0; j < details.size(); j++) {
//                    JSONObject detail = details.getJSONObject(j);
//                    String goodsNo = detail.getString("goodsNo");
//                    JSONArray batches = detail.getJSONArray("batchs");
//                    for (int k = 0; k < batches.size(); k++) {
//                        JSONObject batch = batches.getJSONObject(k);
//                        String qty = batch.getString("qty");
//                        String batchNo = batch.getString("batchNo");
//                        StockDeductionVO vo = new StockDeductionVO();
//                        vo.setWarehouseCode(wareHouseCode);
//                        vo.setGoodsNo(goodsNo);
//                        vo.setBatchNo(batchNo);
//                        vo.setQty(qty);
//                        stockDeductionQueue.offer(vo);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void moveDeductionFileToHistoryPath() {
//        try {
//            if (null == this.sshConnection)
//                this.sshConnection = new JSchConnectUtils(this.fileSystemHost, this.fileSystemPort, this.fileSystemUserName, this.fileSystemPassWord);
//            if (null == this.sshConnection.getSession() || !this.sshConnection.getSession().isConnected())
//                this.sshConnection.connect();
//            if (null != this.sshConnection && null != this.sshConnection.getSession() && this.sshConnection.getSession().isConnected()) {
//                StringBuilder command = new StringBuilder();
//                command.append("mv ");
//                command.append(this.deductionFileDirectory);
//                command.append("*.json");
//                command.append(" ");
//                command.append(this.historyPath);
//                this.sshConnection.execCommand(command.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void run(ApplicationArguments args) throws Exception {
//        this.httpUtil = new HttpUtil();
//    }
//
//    /**
//     * 测试方法
//     */
//    private void testCreateStockVO(List<StockDeductionVO> stockDeductionVOList) {
//        for (int i = 1; i <= 20; i++) {
//            try {
//                Thread.sleep(10L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            StockDeductionVO vo1 = new StockDeductionVO();
//            vo1.setWarehouseCode("10080020Z0101");
//            vo1.setGoodsNo("0101000056");
//            vo1.setBatchNo("CGRK202209050000020001");
//            vo1.setQty("10" + i);
//            this.stockDeductionQueue.offer(vo1);
//        }
//    }
//
//    /**
//     * 测试方法
//     */
//    private void testMoveFileWhenTaskFailure() {
//        (new Thread(() -> {
//            while (true)
//                moveDeductionFileToHistoryPath();
//        })).run();
//    }
//}
