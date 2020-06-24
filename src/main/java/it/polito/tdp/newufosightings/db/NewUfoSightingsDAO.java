package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.Arco;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;
import it.polito.tdp.newufosightings.model.Stato;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings() {
		String sql = "SELECT * FROM sighting";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public List<State> loadAllStates() {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<String> forme(Integer anno){
		
		String sql = "select distinct shape " + 
				"from sighting " + 
				"where year(datetime) =? " + 
				"order by shape asc";
		
		List<String> forme = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				forme.add(rs.getString("shape"));
			}

			conn.close();
			return forme;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Stato> getVertici(Map<String, Stato> idMap){
		String sql = "select id, name " + 
				"from state " + 
				"order by id";
		
		List<Stato> vertici = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Stato stato = new Stato(rs.getString("id"), rs.getString("name"));
				idMap.put(stato.getId(), stato);
				vertici.add(stato);
			}

			conn.close();
			return vertici;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Arco> archi(Map<String, Stato> idMap){ 
		String sql = "select state1, state2 " + 
				"from neighbor " + 
				"where state1 < state2";
		
		List<Arco> archi = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Stato stato1 = idMap.get(rs.getString("state1"));
				Stato stato2 = idMap.get(rs.getString("state2"));
				
				if(stato1!=null && stato2!=null) {
					archi.add(new Arco(stato1, stato2, 0));
				}
			}

			conn.close();
			return archi;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Arco> archiPeso(Map<String, Stato> idMap, Integer anno, String forma){ 
		String sql = "select state1, state2, count(*) as conta " + 
				"from neighbor, sighting s1  " + 
				"where s1.shape = ? " + 
				"and year(s1.datetime) = ? " + 
				"and (s1.`state`=state1 " + 
				"or s1.state= state2) " + 
				"and state1<state2 " + 
				"group by state1,state2";
		
		List<Arco> archi = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, forma);
			st.setInt(2, anno);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Stato stato1 = idMap.get(rs.getString("state1"));
				Stato stato2 = idMap.get(rs.getString("state2"));
				
				if(stato1!=null && stato2!=null) {
					archi.add(new Arco(stato1, stato2, rs.getInt("conta")));
				}
			}

			conn.close();
			return archi;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
}

