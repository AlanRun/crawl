package com.etoak.crawl.page;

public class TeamInfo {
	
	private String club_name;
	private String team_pic;
	private String competition_abbr_name;
	/**
	 * @return the club_name
	 */
	public String getClub_name() {
		return club_name;
	}
	/**
	 * @param club_name the club_name to set
	 */
	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}
	/**
	 * @return the team_pic
	 */
	public String getTeam_pic() {
		return team_pic;
	}
	/**
	 * @param team_pic the team_pic to set
	 */
	public void setTeam_pic(String team_pic) {
		this.team_pic = team_pic;
	}
	/**
	 * @return the competition_abbr_name
	 */
	public String getCompetition_abbr_name() {
		return competition_abbr_name;
	}
	/**
	 * @param competition_abbr_name the competition_abbr_name to set
	 */
	public void setCompetition_abbr_name(String competition_abbr_name) {
		this.competition_abbr_name = competition_abbr_name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TeamInfo [club_name=" + club_name + ", team_pic=" + team_pic + ", competition_abbr_name="
				+ competition_abbr_name + "]";
	}

}
