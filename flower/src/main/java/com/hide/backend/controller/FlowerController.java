package com.hide.backend.controller;

import com.hide.backend.config.Constant;
import com.hide.backend.config.HttpMsg;
import com.hide.backend.dao.FlowersDao;
import com.hide.backend.dao.LoginDao;
import com.hide.backend.entity.*;
import com.hide.backend.service.FlowersService;
import com.hide.backend.util.VerifyUtil;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 商品
 **/
@RestController
@RequestMapping("flower")
public class FlowerController {
    @Autowired
    FlowersService flowerService;

    @Autowired
    FlowersDao flowersDao;

    @RequestMapping("/test")
    public R test() {
        R r = new R();
        return r;
    }

    /**
     * 搜索花束
     * @param page
     * @param searchKey
     * @param searchType
     * @return
     */
    @RequestMapping("/find")
    public R find(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey, @RequestParam("searchType") String searchType) {
        R r = new R();
        List<Flower> flowers = flowerService.find(searchKey, searchType);
        if (flowers == null) {
            return r.setCode(2000);
        }
        return getResponse(flowers, page, Constant.SHOW_PAGE_SIZE, r);
    }

    /**
     * 查询所有花束
     * @param page
     * @param searchKey
     * @return
     */
    @RequestMapping("/findAll")
    public R findAll(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        R r = new R();
        List<Flower> flowers = flowerService.findAll(searchKey);
        if (flowers == null) {
            return r.setCode(2000);
        }
        return getResponse(flowers, page, Constant.PAGE_SIZE, r);
    }

    // 返回结果
    private R getResponse(List<Flower> flowers, int page, int pageSize, R r) {
        Map<String, Object> map = new HashMap<>();
        List<Flower> items = flowers.size() >= page * pageSize ?
                flowers.subList((page - 1) * pageSize, page * pageSize)
                : flowers.subList((page - 1) * pageSize, flowers.size());
        int len = flowers.size() % pageSize == 0 ? flowers.size() / pageSize
                : (flowers.size() / pageSize + 1);
        for (Flower item : items) {
            if (item.getImg_guid() == null) {
                item.setImg_guid(Constant.DEFAULT_IMG);
            }
            item.setImg_guid(Constant.IMG_USE_PATH + item.getImg_guid());
        }
        map.put("items", items);
        map.put("len", len);
        return r.setCode(2000).setData(map);
    }

    @RequestMapping("/create")
    public R create(@RequestBody Flower flower) {
        R r = new R();
        int ans = flowerService.add(flower);
        if (ans == 1) {
            return r.setCode(2000).setMsg(HttpMsg.ADD_FLOWER_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.ADD_FLOWER_FAILED);
    }

    @RequestMapping("/update")
    public R update(@RequestBody Flower flower) {
        R r = new R();
        int ans = flowerService.update(flower);
        if (ans >= 0) {
            return r.setCode(2000).setMsg(HttpMsg.UPDATE_FLOWER_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.UPDATE_FLOWER_FAILED);
    }

    @RequestMapping("/changeState")
    public R changeState(@RequestBody Flower flower) {
        R r = new R();
        flowersDao.changeState(flower);
        String msg = flower.getState() == 1?HttpMsg.GOODS_UP_OK:HttpMsg.GOODS_DOWN_OK;
        return r.setCode(2000).setMsg(msg);
    }

    // 保存上传的图片
    @RequestMapping("/updateImg")
    public R updateImg(@RequestParam("file") MultipartFile file) {
        R r = new R();
        // 只接收 jpg/png 图片
        String filename = file.getOriginalFilename();
        if (!filename.endsWith(".jpg") && !filename.endsWith(".png")){
            return r.setCode(4000).setMsg(HttpMsg.ERROR_FILE_TYPE);
        }
        String img_guid = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        try {
            savePic(file.getInputStream(), img_guid);
            return r.setCode(2000).setMsg(HttpMsg.UPDATE_PIC_OK).setData(img_guid);
        } catch (IOException e) {
            return r.setCode(4000).setMsg(HttpMsg.UPDATE_PIC_FAILED);
        }
    }

    @PutMapping("/updateImgGuid")
    public R updateImgGuid(@RequestParam("guid") String guid, @RequestParam("id") int id) {
        R r = new R();
        int ans = flowerService.updateImg(guid, id);
        if (ans == 1) {
            return r.setCode(2000).setMsg(HttpMsg.UPDATE_PIC_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.UPDATE_PIC_FAILED);
    }

    @DeleteMapping("/delete")
    public R delete(@RequestParam("id") int id) {
        R r = new R();
        int ans = flowerService.delete(id);
        if (ans == 1) {
            return r.setCode(2000).setMsg(HttpMsg.DELETE_FLOWER_OK);
        }
        return r.setCode(4000).setMsg(HttpMsg.DELETE_FLOWER_FAILED);
    }

    private void savePic(InputStream inputStream, String fileName) {
        OutputStream os = null;
        try {
            String path = Constant.IMG_PATH;
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            os = new FileOutputStream(new File(path + fileName));
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

