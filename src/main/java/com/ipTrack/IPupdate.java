/**
 * 
 */
package com.ipTrack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.UUID;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;

/**
 * @author vinod<vinodpal458@gmail.com
 * 
 */
public class IPupdate {
	private Logger logger = Logger.getLogger(IPupdate.class);

	public String getUpdateIpTrack(String ipAddress, String ipLocation, String nbmds_session_last_token_id,
			String filterLocation) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResourceBundle resource = null;
		String query = null;
		String type = null;
		String ip_row_id = null;
		int ip_count = 0;
		if (ipAddress == null) {
			logger.error("unable to find Ip Address.");
			ipAddress = "null";
		}
		try {
			resource = ResourceBundle.getBundle("properties.query");
			query = resource.getString("ip_track");
			connection = (new ConnectionCreate()).getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ipAddress);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ip_row_id = resultSet.getString(1);
				ip_count = resultSet.getInt(2);
				++ip_count;
			} else {
				type = "c";
				ip_row_id = "ip_" + UUID.randomUUID().toString();
				ip_count = 1;
			}
			if ("c".equalsIgnoreCase(type)) {
				query = resource.getString("ipInsert");
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, nbmds_session_last_token_id);
				preparedStatement.setString(2, ipLocation);
				preparedStatement.setInt(3, ip_count);
				preparedStatement.setString(4, ipAddress);
				preparedStatement.setString(5, ip_row_id);
				int resultTest = preparedStatement.executeUpdate();
				if (resultTest > 0) {
					logger.info("success to insert into ipTrack.");
					System.out.println("success");
				} else {
					logger.info("success to update into ipTrack.");
					System.out.println("unsuccess");
				}
			} else {
				query = resource.getString("ipUpdate");
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, nbmds_session_last_token_id);
				preparedStatement.setString(2, ipLocation);
				preparedStatement.setInt(3, ip_count);
				preparedStatement.setString(4, ipAddress);
				preparedStatement.setString(5, ip_row_id);

				int resultTest = preparedStatement.executeUpdate();
				if (resultTest > 0) {
					logger.info("success to insert into ipTrack.");
					// System.out.println("success");
				} else {
					logger.info("success to update into ipTrack.");
					// System.out.println("unsuccess");
				}
			}
			if (filterLocation == null || "null".equalsIgnoreCase(filterLocation) || filterLocation.length() < 1)
				return "success";
			else
				return getUpdateIpFilterLocation(filterLocation, ip_row_id, connection, resource);
		} catch (NullPointerException nullPointerException) {
			logger.error("Failed to update into database.", nullPointerException);
		} catch (Exception exception) {
			logger.error("Failed to update into database.", exception);
		} finally {
			try {
				if (connection != null) {
					connection.close();
					connection = null;
				}
				if (preparedStatement != null) {
					preparedStatement.close();
					preparedStatement = null;
				}
				if (resultSet != null) {
					resultSet.close();
					resultSet = null;
				}
			} catch (Exception exception) {
				logger.error("Failed to close preparedStatement.", exception);
			}
		}
		return "unsuccess";
	}

	private String getUpdateIpFilterLocation(String filterLocation, String ip_row_id, Connection connection,
			ResourceBundle resource) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String type = null;
		String query = null;
		int countFilterLocation = 0;
		try {
			if (resource == null)
				resource = ResourceBundle.getBundle("properties.query");
			if (connection == null) {
				connection = (new ConnectionCreate()).getConnection();
			}
			filterLocation = filterLocation.toLowerCase();
			query = resource.getString("ip_filter_locaton_count");
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ip_row_id);
			preparedStatement.setString(2, filterLocation);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				countFilterLocation = resultSet.getInt(1);
				++countFilterLocation;
			} else {
				type = "c";
				countFilterLocation = 1;
			}
			if ("c".equalsIgnoreCase(type)) {
				query = resource.getString("ip_filter_locaton_insert");
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, ip_row_id);
				preparedStatement.setString(2, filterLocation);
				preparedStatement.setInt(3, countFilterLocation);
				int resultTest = preparedStatement.executeUpdate();
				if (resultTest > 0) {
					// System.out.println("success");
					return "success";
				} else {
					// System.out.println("unsuccess");
					return "unsuccess";
				}
			} else {
				query = resource.getString("ip_filter_locaton_update");
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, countFilterLocation);
				preparedStatement.setString(2, filterLocation);
				preparedStatement.setString(3, ip_row_id);
				int resultTest = preparedStatement.executeUpdate();
				if (resultTest > 0) {
					// System.out.println("success");
					return "success";

				} else {
					// System.out.println("unsuccess");
					return "unsuccess";
				}
			}
		} catch (NullPointerException nullPointerException) {
			logger.error("Failed to update for Ip Filter Location.", nullPointerException);
		} catch (Exception exception) {
			logger.error("Failed to update for Ip Filter Location.", exception);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
					preparedStatement = null;
				}
				if (resultSet != null) {
					resultSet.close();
					resultSet = null;
				}
			} catch (Exception exception) {
				logger.error("Failed to close preparedStatement.", exception);
			}
		}
		return "unsuccess";
	}
}
