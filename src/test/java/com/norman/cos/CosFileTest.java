package com.norman.cos;

import com.norman.BaseSpringBootTest;
import com.norman.config.COSFileConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/12/4 4:24 PM.
 */

public class CosFileTest extends BaseSpringBootTest {

    @Autowired
    COSFileConfig cosFileConfig;

    @Autowired
    COSClient cosClient;

//        String key = "upload_single_demo.txt";
//    String key = "1111.jpeg";
    String key = "len5M.txt";

    @Test
    public void uploadFile() {
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
//        File localFile = new File("src/test/resources/len5M.txt");
        File localFile = new File("src/test/resources/" + key);

        System.out.println(localFile.getName());
        // 指定要上传到 COS 上对象键
        // 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosFileConfig.getBucket(), key, localFile);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        System.out.println(putObjectResult);

        //bzntestbucket01-1252512960.cos.ap-beijing.myqcloud.com/upload_single_demo.txt
    }

    @Test
    public void uploadFileStream() throws Exception {
        File localFile = new File("src/test/resources/" + key);
        final byte[] bytes = FileUtils.readFileToByteArray(localFile);

//        InputStream input = new ByteArrayInputStream(new byte[10]);
        InputStream input = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(bytes.length);
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
//        objectMetadata.setContentType("image/jpeg");

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(cosFileConfig.getBucket(), key, input, objectMetadata);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        System.out.println(putObjectResult.getETag());


    }

    @Test
    public void downloadFile() {
        // 指定要下载到的本地路径
        File downFile = new File("src/test/resources/download" + key);
        // 指定要下载的文件所在的 bucket 和对象键
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosFileConfig.getBucket(), key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
    }

    @Test
    public void downloadFileStream() throws Exception{
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosFileConfig.getBucket(), key);
        final COSObject object = cosClient.getObject(getObjectRequest);
        System.out.println("bucket name: " + object.getBucketName() +
                ", key=" + object.getKey() + ", content-type=" + object.getObjectMetadata().getContentType());
//        final COSObjectInputStream inputStream = object.getObjectContent();
        BufferedOutputStream bufferedOutput = null;
        FileOutputStream fileOutputStream = null;
        try(COSObjectInputStream inputStream = object.getObjectContent()){
            File downFile = new File("src/test/resources/download2" + key);
            fileOutputStream = new FileOutputStream(downFile);
            bufferedOutput = new BufferedOutputStream(fileOutputStream);
            IOUtils.copy(inputStream, bufferedOutput);
        } finally {
            IOUtils.closeQuietly(bufferedOutput);
            IOUtils.closeQuietly(fileOutputStream);
        }

    }

    @Test
    public void deleteFile() {

        cosClient.deleteObject(cosFileConfig.getBucket(), key);

    }


    @After
    public void tearDown() throws Exception {
        this.cosClient.shutdown();
        super.tearDown();
    }


}
