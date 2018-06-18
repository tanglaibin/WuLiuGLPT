package tj.ljjtlb.action.chart;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import tj.ljjtlb.action.BaseAction;
import tj.ljjtlb.utils.file.FileUtil;
import tl.ljjtlb.dao.SqlDao;

public class StatChartAction extends BaseAction{

	private SqlDao sqldao;
	public void setSqldao(SqlDao sqldao) {
		this.sqldao = sqldao;
	}


	
	public String factorysale() throws IOException{
		String sql="SELECT FACTORY_NAME,SUM(AMOUNT) AS SAMOUNT FROM contract_product_c GROUP BY FACTORY_NAME ORDER BY SAMOUNT DESC";
		List<String> list = sqldao.executeSQL(sql);
		
		int j=list.size()-12;
		Double sum=0.00;
		
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<pie>");
		
        for (int i = 0; i < j; i++) {
        	sb.append("<slice title=\""+list.get(i)+"\" pull_out=\"true\">"+list.get(++i)+"</slice>");
		}
        
        for(int i = j; i < list.size(); i++){
        	sum += Double.parseDouble(list.get(++i));
        }
        
        String sum2 = String.valueOf(sum);
    	sb.append("<slice title=\"其他\" pull_out=\"true\">"+sum2+"</slice>");
        
		sb.append("</pie>");
		
		FileUtil fileu=new FileUtil();
		String spath=ServletActionContext.getServletContext().getRealPath("/");
		fileu.createTxt(spath, "stat\\chart\\factorysale\\data.xml", sb.toString(), "UTF-8");
		
		return "facsale";
	}
	
	
	public String productsale() throws IOException{
		String sql="SELECT PRODUCT_NO,SUM(AMOUNT) AS PAMOUNT FROM contract_product_c GROUP BY PRODUCT_NO ORDER BY PAMOUNT DESC LIMIT 15";
		List<String> list = sqldao.executeSQL(sql);
		
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    sb.append("<chart><series>");
	    
	    int j=0;
	    for(int i=0;i<list.size();i++){
	    	sb.append("<value xid=\""+(j++)+"\">"+list.get(i)+"</value>");
	    	i++;
	    }
	    
	    sb.append("</series><graphs><graph gid=\"30\" color=\"#FFCC00\" gradient_fill_colors=\"#111111, #1A897C\">");
	    
	    j=0;
	    for(int i=0;i<list.size();i++){
	    	i++;
	    	sb.append("<value xid=\""+(j++)+"\" description=\"\" url=\"\">"+list.get(i)+"</value>");
	    }
	    
	    sb.append("</graph></graphs>");
	    sb.append("<labels><label lid=\"0\"><x>0</x><y>20</y><rotate></rotate><width></width><align>center</align><text_color></text_color><text_size></text_size><text><![CDATA[<b>JavaEE28期产品销量排名</b>]]></text></label></labels>");
	    sb.append("</chart>");
	    
	    FileUtil fileu=new FileUtil();
		String spath=ServletActionContext.getServletContext().getRealPath("/");
		fileu.createTxt(spath, "stat\\chart\\productsale\\data.xml", sb.toString(), "UTF-8");
	    
		return "productsale";
	}
	
	
	public String onlineinfo() throws IOException{
		String sql= "SELECT a.a1,IFNULL(b.c,0) c FROM (SELECT * FROM online_info_t) a LEFT JOIN (SELECT DATE_FORMAT(LOGIN_TIME,'%H') a1, COUNT(*) c FROM login_log_p GROUP BY DATE_FORMAT(LOGIN_TIME,'%H')) b ON (a.a1=b.a1)";
		List<String> list = sqldao.executeSQL(sql);
		
		
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    sb.append("<chart><series>");
	    
	    int j=0;
	    for(int i=0;i<list.size();i++){
	    	sb.append("<value xid=\""+(j++)+"\">"+list.get(i)+"</value>");
	    	i++;
	    }
	    
	    sb.append("</series><graphs><graph gid=\"30\" color=\"#FFCC00\" gradient_fill_colors=\"#111111, #1A897C\">");
	    
	    j=0;
	    for(int i=0;i<list.size();i++){
	    	i++;
	    	sb.append("<value xid=\""+(j++)+"\" description=\"\" url=\"\">"+list.get(i)+"</value>");
	    }
	    
	    sb.append("</graph></graphs>");
	    sb.append("</chart>");
	    
	    FileUtil fileu=new FileUtil();
		String spath=ServletActionContext.getServletContext().getRealPath("/");
		fileu.createTxt(spath, "stat\\chart\\onlineinfo\\data.xml", sb.toString(), "UTF-8");
		
		return "onlineinfo";
	}
	
	
	public String ipinfo() throws IOException{
		String sql="SELECT ip_address, COUNT(*) c  FROM login_log_p GROUP BY ip_address ORDER BY COUNT(*) DESC LIMIT 10";
		List<String> list = sqldao.executeSQL(sql);
		
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    sb.append("<chart><series>");
	    
	    int j=0;
	    for(int i=0;i<list.size();i++){
	    	sb.append("<value xid=\""+(j++)+"\">"+list.get(i)+"</value>");
	    	i++;
	    }
	    
	    sb.append("</series><graphs><graph gid=\"30\" color=\"#FFCC00\" gradient_fill_colors=\"#111111, #1A897C\">");
	    
	    j=0;
	    for(int i=0;i<list.size();i++){
	    	i++;
	    	sb.append("<value xid=\""+(j++)+"\" description=\"\" url=\"\">"+list.get(i)+"</value>");
	    }
	    
	    sb.append("</graph></graphs>");
	    sb.append("<labels><label lid=\"0\"><x>0</x><y>20</y><rotate></rotate><width></width><align>center</align><text_color></text_color><text_size></text_size><text><![CDATA[<b>网站访问ip地址排名前十名</b>]]></text></label></labels>");
	    sb.append("</chart>");
	    
	    FileUtil fileu=new FileUtil();
		String spath=ServletActionContext.getServletContext().getRealPath("/");
		fileu.createTxt(spath, "stat\\chart\\ipinfo\\data.xml", sb.toString(), "UTF-8");
		
		return "ipinfo";
	}
	
}
