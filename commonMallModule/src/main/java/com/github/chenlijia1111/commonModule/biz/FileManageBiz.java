package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.utils.WebUtils;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.core.enums.SystemPropertyEnum;
import com.github.chenlijia1111.utils.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 文件管理
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/12/3 0003 下午 2:13
 **/
@Service
public class FileManageBiz {

    @Value("${upload.imgSavePath}")
    private String imgSavePath;

    @Value("${upload.fileSavePath}")
    private String fileSavePath;


    /**
     * 上传文件接口
     *
     * @param fileType   文件类型  img/file 用于区分保存的文件夹
     * @param request
     * @param file
     * @param isFileName 是否需要源文件名称
     * @return
     */
    public Result uploadFile(String fileType, HttpServletRequest request, MultipartFile file,
                             boolean isFileName) {
        Result result = WebUtils.saveFile(request, file, Objects.equals(fileType, "img") ? getImgSavePath() : getFileSavePath(),
                "system/downLoad", isFileName, fileType);
        return result;
    }


    /**
     * 批量上传文件接口
     * <p>
     * 前端代码如下
     * <input type="file" name="file" multiple="multiple" onchange="showPreview(this)">
     * function showPreview(source) {
     * var files = source.files;
     * console.log(files);
     * <p>
     * var formData = new FormData();
     * formData.append("fileType", "file");
     * formData.append("isfileName", "false");
     * for (var i = 0; i < files.length; i++) {
     * formData.append("files", files[i]);
     * }
     * $.ajax(
     * {
     * url: "http://127.0.0.1:8087/ende/system/upload/batch",
     * data: formData,
     * type: "POST",
     * processData: false,
     * contentType: false,
     * success: function (res) {
     * console.log(res);
     * }
     * }
     * );
     * }
     *
     * @param fileType   文件类型  img/file
     * @param request
     * @param files
     * @param isFileName 是否需要源文件名称
     * @return
     */
    public Result batchUploadFile(String fileType, HttpServletRequest request,
                                  MultipartFile[] files,
                                  boolean isFileName) {

        if (null == files || files.length == 0) {
            return Result.failure("上传文件为空");
        }

        ArrayList<Object> resultPath = new ArrayList<>();
        for (MultipartFile file : files) {
            Result result = WebUtils.saveFile(request, file, Objects.equals(fileType, "img") ? getImgSavePath() : getFileSavePath(),
                    "system/downLoad", isFileName, fileType);
            if (!result.getSuccess()) {
                return result;
            }
            resultPath.add(result.getData());

        }
        return Result.success("上传成功", resultPath);
    }

    /**
     * @param fileType 文件类型 img,qr,file
     * @param filePath 文件地址 201912021212121212121212  前面的表示yyyyMMddHHmmss
     * @param fileName 文件下载返回名称
     * @param isDelete 下载之后是否删除
     * @param response
     */
    public void downLoadFile(String fileType, String filePath, String fileName, boolean isDelete,
                             HttpServletResponse response, HttpServletRequest request) {

        String pathSuffix = fileSavePath;
        switch (fileType) {
            case "img":
                pathSuffix = imgSavePath;
                break;
            case "file":
                pathSuffix = fileSavePath;
                break;
        }
        //二维码保存路径为绝对路径，不需要前缀
        //获取文件保存的路径
        if (StringUtils.isEmpty(filePath)) {
            //文件路径为空
            ResponseUtil.printRest(Result.failure("文件地址为空"), response);
            return;
        }
        //开始判断文件地址
        if (filePath.length() < 14) {
            ResponseUtil.printRest(Result.failure("文件地址不合法"), response);
            return;
        }
        String year = filePath.substring(0, 4);
        String month = filePath.substring(4, 6);
        String day = filePath.substring(6, 8);
        StringBuilder fileAbsPath = new StringBuilder();
        fileAbsPath.append(pathSuffix);
        fileAbsPath.append("/");
        fileAbsPath.append(year);
        fileAbsPath.append("/");
        fileAbsPath.append(month);
        fileAbsPath.append("/");
        fileAbsPath.append(day);
        fileAbsPath.append("/");
        fileAbsPath.append(filePath);
        WebUtils.downloadFile(fileAbsPath.toString(), fileName, isDelete, response, request);
    }


    /**
     * 获取默认图片保存地址
     *
     * @return java.lang.String
     * @since 下午 2:16 2019/12/3 0003
     **/
    public String getImgSavePath() {
        if (StringUtils.isEmpty(this.imgSavePath)) {
            //获取当前用户根目录
            String userHome = System.getProperty(SystemPropertyEnum.USER_HOME.getName());
            this.imgSavePath = userHome + "/upload/image";
        }
        return this.imgSavePath;
    }

    /**
     * 获取默认文件保存地址
     * windows C:/upload/file
     * linux /home/file
     *
     * @return java.lang.String
     * @since 下午 2:16 2019/12/3 0003
     **/
    public String getFileSavePath() {
        if (StringUtils.isEmpty(this.fileSavePath)) {
            //获取当前用户根目录
            String userHome = System.getProperty(SystemPropertyEnum.USER_HOME.getName());
            this.fileSavePath = userHome + "/upload/file";
        }
        return this.fileSavePath;
    }

}
