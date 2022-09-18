package com.fancv;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class OssVersionDemo {
    public static void main(String[] args) throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-zhangjiakou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5t5sF7sXqD8by7p3RCRB";
        String accessKeySecret = "mwTXDxnvGGO6V5YcMzp80q8a5oH67l";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "fancv-sys";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。 按照orgId 年月日存储文件
        String objectName = "exampledir/5.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

       // upload(bucketName, objectName, ossClient);

        allFiles(ossClient,objectName);
    }

    private static void upload(String bucketName, String objectName, OSS ossClient) {
        try {
            // 以上传字符串为例。
            String content = "Hello OSS 5";
            PutObjectResult result = ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
            // 查看此次上传Object的VersionId。
            System.out.println("result.versionid: " + result.getVersionId());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }



    public static void allFiles(OSS ossClient, String objectName){
        String bucketName = "fancv-sys";
        try {
            // 列举包括删除标记在内的所有Object的版本信息。
            String nextKeyMarker = null;
            String nextVersionMarker = null;
            VersionListing versionListing = null;
            do {
                ListVersionsRequest listVersionsRequest = new ListVersionsRequest()
                        .withBucketName(bucketName)
                        .withKeyMarker(nextKeyMarker)
                        .withVersionIdMarker(nextVersionMarker);

                versionListing = ossClient.listVersions(listVersionsRequest);
                for (OSSVersionSummary ossVersion : versionListing.getVersionSummaries()) {
                    System.out.println("key name: " + ossVersion.getKey());
                    System.out.println("versionid: " + ossVersion.getVersionId());
                    System.out.println("Is latest: " + ossVersion.isLatest());
                    System.out.println("Is delete marker: " + ossVersion.isDeleteMarker());
                }

                nextKeyMarker = versionListing.getNextKeyMarker();
                nextVersionMarker = versionListing.getNextVersionIdMarker();
            } while (versionListing.isTruncated());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public static void download(OSS ossClient, String objectName){
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "fancv-sys";
        try {
            // 封装GetObject请求。
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectName);
            getObjectRequest.setVersionId("CAEQNxiBgICmp5OcjxgiIGJiOGYxZTNmZWQxNjQ1MDE5NTRmNjRkZDEzY2MyMWEx");
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(getObjectRequest);

            // 查看已下载Object的VersionId。
            System.out.println("Get Object versionid:" +  ossObject.getObjectMetadata().getVersionId());
            // 读取指定VersionId的Object内容。
            System.out.println("Object content:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                System.out.println("\n" + line);
            }

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (Throwable ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            // 关闭OSSClient。
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}