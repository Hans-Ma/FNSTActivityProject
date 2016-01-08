package cn.org.njsoft.dao;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.stereotype.Service;

import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.model.User;

/**
 * 2015/12/10
 * ExcelDao接口
 * @see cn.org.njsoft.dao#ExcelDao
 * @author YXF
 *
 */
@Service
public interface ExcelDao {
	public List<User> readExcel(String path) throws Exception;
	// 从excel中读取文件,处理为文件流的形式，读取取出数据，存放在list里面
	public String getValue(XSSFCell xssfRow);
	//判断单元格的数据为什么类型，统一转换为string类型
	public String generateExcel(List<SignIn> signInList, String excelName) throws Exception;
	// 通过传过来的生成signInList生产Excel

}
