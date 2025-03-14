package  com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;
import com.sath.dbconnectin.Commonjedis;

public class Placeorder extends ActionSupport implements SessionAware {
	private Map<String, Object> session;

	private String responseMessage;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String execute() {
		// TODO Auto-generated method stub
		int cart_id = (int) session.get("cart_id");
		System.out.println(cart_id + "viewcart");
		int cus_id = (int) session.get("generatedId");
		System.out.println(cart_id + "viewcart");
		String query = "UPDATE cart_user SET status = 1 WHERE cart_id=? and customer_id=?";
		int updatedRows=0;
		try {
			updatedRows=CommonSqlSet.updatequery(query,cart_id,cus_id);

			
			if (updatedRows > 0) {
				responseMessage="success";
			} else {
				responseMessage="failuer";
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseMessage="error";
			return ERROR;
		}/*finally {
			String fetchcart_id = ("cart:" + cart_id);
			Map<String, String> checkredis = Commonjedis.hashget(fetchcart_id);
			if (checkredis != null && !checkredis.isEmpty()) {
				Commonjedis.hashdel(fetchcart_id);
				System.out.println("place order cart_ redis removed");

			}
		}*/
		return SUCCESS;
	}
}
