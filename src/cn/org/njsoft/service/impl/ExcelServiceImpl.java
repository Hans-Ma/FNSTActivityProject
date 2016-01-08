package cn.org.njsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.ExcelDao;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.ExcelService;
/**
 * 2015/12/11
 * 处理Excel的excelServiceImpl
 * 用于：
 * 	1、将指定格式的公会会员信息 Excel 文件导入系统。
 * 	2、在系统界面结束活动，可生成本次活动的详细信息报表
 * @see cn.org.njsoft.service.impl#ExcelServiceImpl
 * @author YXF
 *
 */
@Scope("singleton")
@Service("excelServiceImpl")
public class ExcelServiceImpl implements ExcelService {
	@Autowired
	private ExcelDao excelDao;
	/**
	 * 
	 * 2015/12/16
	 * 根据上传的excel文件的路径读取excel,放回List<User>
	 * @see cn.org.njsoft.dao.ExcelService#readExcel(String)
	 * @author YXF
	 */
	public List<User> readExcel(String path) throws Exception{
		return excelDao.readExcel(path);
	}
	
	@Override
	/**
	 * 2015/12/16
	 * 通过传过来的生成signInList生产Excel
	 * @see cn.org.njsoft.dao.ExcelService#generateExcel(List<SignIn>)
	 * @author YXF
	 */
	public String generateExcel(List<SignIn> signInList,String excelName) throws Exception {
		return excelDao.generateExcel(signInList, excelName);
	}
}
