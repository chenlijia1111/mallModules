package com.github.chenlijia1111.commonModule.utils;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.FileUtils;
import com.github.chenlijia1111.utils.core.RandomUtil;
import com.github.chenlijia1111.utils.http.HttpUtils;
import com.github.chenlijia1111.utils.image.ReduceImageUtil;
import com.github.chenlijia1111.utils.list.Lists;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * web项目工具类
 * 文件上传下载等
 *
 * @since 上午 9:07 2019/10/30 0030
 **/
public class WebUtils {


    private static Logger log = LoggerFactory.getLogger(WebUtils.class);


    /**
     * @param request
     * @param file
     * @param savePath        保存文件的路径
     * @param downLoadApiPath 下载的api路径
     * @param isfileName      是否加文件名参数
     * @return
     */
    public static Result saveFile(HttpServletRequest request, MultipartFile file, String savePath, String downLoadApiPath, boolean isfileName, String fileType) {
        if (file == null) {
            return Result.failure("上传文件为空");
        }
        if (!StringUtils.hasText(savePath)) {
            return Result.failure("上传路径为空");
        }
        //随机名称
        String newFileName = RandomUtil.createRandomName();
        String originalFilename = file.getOriginalFilename();
        //后缀
        int i = originalFilename.lastIndexOf(".");
        String suffixName = "";
        if (i != -1)
            suffixName = originalFilename.substring(i);

        if (!StringUtils.hasText(suffixName) || suffixName.equals("."))
            return Result.failure("文件没有后缀名");

        FileUtils.checkDirectory(savePath);
        //保存的文件夹,以年月日进行文件夹的区分,防止一个文件夹文件数量过多
        DateTime now = DateTime.now();
        String saveDir = now.toString("yyyy/MM/dd");
        File destFile = new File(savePath + "/" + saveDir + "/" + newFileName + suffixName);
        //校验文件夹是否存在
        FileUtils.checkDirectory(destFile.getParent());
        try {
            file.transferTo(destFile);

            //如果是图片的话,判断图片是否合法
            if (Objects.equals(fileType, "img")) {
                boolean image = FileUtils.isImage(destFile);
                if (!image) {
                    return Result.failure("图片不合法");
                }
            }

            //如果是图片,对图片进行压缩
            if (Lists.asList(".jpg", ".png", ".gif").contains(suffixName)) {
                //多线程执行,这个操作耗时比较长
                new Thread(() -> ReduceImageUtil.reduceImage(destFile)).start();
            }
        } catch (IOException e) {
            log.error("文件上传失败");
            e.printStackTrace();
            return Result.failure("上传失败");
        }
        //返回文件下载地址,不加具体的host前缀,方便迁移
        String path = downLoadApiPath + "?filePath=" + newFileName + suffixName + "&fileType=" + fileType;
        //凡是文件的都需要源文件名
        if (isfileName) {
            path += "&fileName=" + originalFilename;
        }
        Result result = Result.success("上传成功");
        result.setData(path);
        return result;
    }


    /**
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @param isDelete 下载完成之后是否删除文件
     * @param response
     */
    public static void downloadFile(String filePath, String fileName, boolean isDelete, HttpServletResponse response, HttpServletRequest request) {
        File file = new File(filePath);
        if (!file.exists())
            //文件不存在
            return;

        //判断文件类型，赋予MIME Type 浏览器可以根据这个判断是什么类型的文件
        String fileSuffix = FileUtils.getFileSuffix(file).toLowerCase();
        String s = FileUtils.commonDownLoadContentType.get(fileSuffix);
        response.setContentType(StringUtils.hasText(s) ? s : "APPLICATION/OCTET-STREAM");
        //检测是否断点续传
        checkGoingDown(file, request, response);

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
             OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            if (StringUtils.hasText(fileName)) {
                fileName = URLDecoder.decode(fileName, "UTF-8");
                if (HttpUtils.isIE(request)) {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                } else {
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            }
            byte[] buffer = new byte[2048];
            //起始读取位置
            Integer startPosition = findDownLoadStartPosition(request);
            inputStream.skip(startPosition);
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            log.error("文件未找到:" + filePath);
        } catch (IOException e) {
            log.error("文件下载异常:" + e.getMessage());
//            e.printStackTrace();
        } finally {
            if (isDelete)
                file.delete();
        }

    }


    /**
     * 处理断点续传
     * 返回状态 206
     * 新增 header Content-Range=bytes 2000070-106786027/106786028
     *
     * @param file     1
     * @param request  2
     * @param response 3
     * @return void
     * @author chenlijia
     * @since 下午 4:39 2019/7/23 0023
     **/
    private static void checkGoingDown(File file, HttpServletRequest request, HttpServletResponse response) {
        long length = file.length();
        String range = request.getHeader("range");
        if (StringUtils.hasText(range)) {
            Integer start = findDownLoadStartPosition(request);
            response.setHeader("Content-Range", "bytes " + start + "-" + length + "/" + length);
            response.setStatus(206);
            //文件大小
            response.setContentLengthLong(file.length() - start);
        } else {
            //文件大小
            response.setContentLengthLong(file.length());
        }
    }


    /**
     * 获取下载起始位置
     *
     * @param request 1
     * @return java.lang.Integer
     * @author chenlijia
     * @since 下午 4:41 2019/7/23 0023
     **/
    public static Integer findDownLoadStartPosition(HttpServletRequest request) {

        int start = 0;
        String range = request.getHeader("range"); //Range: bytes=2001-4932
        if (StringUtils.hasText(range)) {
            String[] split = range.split("=");
            String s = split[1]; //2001-4932
            if (StringUtils.hasText(s)) {
                String[] split1 = s.split("-");
                if (null != split1 && split1.length > 0) {
                    start = Integer.valueOf(split1[0]);
                }
            }
        }
        return start;
    }


    /**
     * @param sourceFilePath  要压缩的文件夹
     * @param destZipFilePath 压缩包存放路径
     * @param destFileName    压缩包名
     * @return
     */
    public static Result fileToZip(String sourceFilePath, String destZipFilePath, String destFileName) {
        File sourceFiles = new File(sourceFilePath);
        if (!sourceFiles.exists()) {
            //文件夹不存在
            return Result.failure("文件夹不存在");
        }

        File[] files = sourceFiles.listFiles();
        if (files == null || files.length == 0) {
            return Result.failure("文件夹为空");
        }

        File file1 = new File(destZipFilePath);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        File destFile = new File(destZipFilePath + "/" + destFileName + ".zip");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)))) {

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                //创建zip实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] bs = new byte[4096];
                int read = -1;
                while ((read = bufferedInputStream.read(bs, 0, 4096)) != -1) {
                    zipOutputStream.write(bs, 0, read);
                }
            }
            Result success = Result.success("压缩成功");
            success.setData(destZipFilePath + "/" + destFileName + ".zip");
            return success;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.failure("压缩失败");
    }


}
