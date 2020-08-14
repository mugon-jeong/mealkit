package dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import vo.Material;

public class MaterialDao {
	private MaterialDao() {}
	private static MaterialDao instance = new MaterialDao();
	public static MaterialDao getInstance() {
		return instance;
	}
	
	public List<Material> selectAll(){
		List<Material> list=new ArrayList<Material>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select * from mat";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				Material material=new Material();
				material.setMat_no(rs.getString("mat_no"));
				material.setMat_idx(rs.getString("mat_idx"));
				material.setMat_nm(rs.getString("mat_nm"));
				material.setMat_price(rs.getInt("mat_price"));
				material.setMat_unit(rs.getInt("mat_unit"));
				material.setMat_image(rs.getString("mat_image"));
				System.out.println(material);
				list.add(material);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConn.close(conn, ps, rs);
		}
		return list;
	}
	//idx로 선택
	public Material selectOne(String idx){
		Material material=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select * from mat where mat_idx=?";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setString(1, idx);
			rs=ps.executeQuery();
			while(rs.next()) {
				material=new Material();
				material.setMat_no(rs.getString("mat_no"));
				material.setMat_idx(rs.getString("mat_idx"));
				material.setMat_nm(rs.getString("mat_nm"));
				material.setMat_price(rs.getInt("mat_price"));
				material.setMat_unit(rs.getInt("mat_unit"));
				material.setMat_image(rs.getString("mat_image"));
				System.out.println(material);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConn.close(conn, ps, rs);
		}
		return material;
	}
	
	public boolean insert(Material material) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql= "insert into mat(mat_no, mat_idx, mat_nm, mat_price, mat_unit,mat_image) VALUES (?, ?, ?, ?, ?,?)";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setNString(1, material.getMat_no());
			ps.setNString(2, material.getMat_idx());
			ps.setNString(3, material.getMat_nm());
			ps.setInt(4, material.getMat_price());
			ps.setInt(5, material.getMat_unit());
			ps.setNString(6, material.getMat_image());
			int n=ps.executeUpdate();
			if(n==1) {
				flag=true;
				System.out.println("데이터 입력 성공");
			}else {
				System.out.println("데이터 입력 실패");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}finally {
			try {
				DBConn.close(conn, ps);
			}catch (Exception e) {
				// TODO: handle exception
				e.getStackTrace();
			}
		}
		return flag;
	}
	//업데이트 idx로 검색 가격,단위,사진만 가능
	public boolean update(Material material) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql= "update mat set mat_price = ?, mat_unit = ?, mat_image =? where mat_idx=?";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, material.getMat_price());
			ps.setInt(2, material.getMat_unit());
			ps.setNString(3, material.getMat_image());
			ps.setNString(4, material.getMat_idx());
			int n=ps.executeUpdate();
			if(n==1) {
				flag=true;
				System.out.println("업데이트 성공");
			}else {
				System.out.println("업데이트 실패");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}finally {
			try {
				DBConn.close(conn, ps);
			}catch (Exception e) {
				// TODO: handle exception
				e.getStackTrace();
			}
		}
		return flag;
	}
	//idx로 삭제
	public boolean delete(String idx) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql= "delete from mat where mat_idx=?";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setNString(1, idx);
			int n=ps.executeUpdate();
			if(n==1) {
				flag=true;
				System.out.println("삭제 성공");
			}else {
				System.out.println("삭제 실패");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}finally {
			try {
				DBConn.close(conn, ps);
			}catch (Exception e) {
				// TODO: handle exception
				e.getStackTrace();
			}
		}
		return flag;
	}
	// 파일 업로드 
	  	public Map<String, String> upload(HttpServletRequest request, HttpServletResponse response,String FILE_REPO) throws ServletException, IOException {
	  		Map<String, String> matetrialMap = new HashMap<String, String>();
	  		String encoding = "UTF-8";
	  		File currentDirPath = new File(FILE_REPO);
	  		DiskFileItemFactory factory = new DiskFileItemFactory();
	  		factory.setRepository(currentDirPath);
	  		factory.setSizeThreshold(1024*1024*5); //5GB
	  		factory.setDefaultCharset(encoding); //파일올라올때 인코딩
	  		ServletFileUpload upload = new ServletFileUpload(factory);
	  		try {
	  			List<FileItem> items = upload.parseRequest(request);
	  			for(int i = 0 ; i < items.size() ; i++) {
	  				FileItem item = (FileItem)items.get(i);
	  				if(item.isFormField()) {
	  					System.out.println(item.getFieldName() + ":" + item.getString());
	  					matetrialMap.put(item.getFieldName(), item.getString());
	  				} else {
	  					System.out.println("파라미터명: " + item.getFieldName());
	  					System.out.println("파일명: " + item.getName());
	  					System.out.println("파일의 크기: " + item.getSize());
	  					
	  					if(item.getSize() > 0) {
	  						int idx = item.getName().lastIndexOf("\\"); //윈도우시스템
	  						if(idx == -1) {
	  							idx = item.getName().lastIndexOf("/"); //리눅스시스템 파일 마지막 부분 
	  						}
	  						String fileName = item.getName().substring(idx + 1);
	  						File uploadFile = new File(currentDirPath + "\\" + fileName);
	  						matetrialMap.put(item.getFieldName(), fileName);
	  						item.write(uploadFile);
	  					}
	  				}
	  			}
	  		} catch (Exception e) {
	  			e.printStackTrace();
	  		}
	  		return matetrialMap;
	  	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
