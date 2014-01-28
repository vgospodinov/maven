package com.initech.ini.maven.bo;


/**
 *  An iniBuildProfile is a combination of a client and a stage 
 *
 */
public class IniBuildProfile {

	private final String client;
	private final String stage;
	
	public IniBuildProfile(String client, String stage) {
		this.client = client;
		this.stage = stage;
	}

	@Override
	public String toString() {
		return client + "-" + stage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((stage == null) ? 0 : stage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IniBuildProfile other = (IniBuildProfile) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (stage == null) {
			if (other.stage != null)
				return false;
		} else if (!stage.equals(other.stage))
			return false;
		return true;
	}

	
	public String getStage() {
		return stage;
	}

	public String getClient() {
		return client;
	}
	
	

	
}
