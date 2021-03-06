package me.skiincraft.api.ousu.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.skiincraft.api.ousu.OusuAPI;
import me.skiincraft.api.ousu.requests.Request;
import me.skiincraft.api.ousu.entity.beatmap.Beatmap;
import me.skiincraft.api.ousu.entity.multiplayer.Game;
import me.skiincraft.api.ousu.entity.multiplayer.Match;
import me.skiincraft.api.ousu.entity.multiplayer.MultiplayerScore;
import me.skiincraft.api.ousu.entity.objects.Gamemode;
import me.skiincraft.api.ousu.entity.objects.Mods;
import me.skiincraft.api.ousu.entity.objects.Scoring;
import me.skiincraft.api.ousu.entity.objects.Team;

public class GameImpl implements Game {
	
	private final JsonObject object;
	private final Match match;
	private final OusuAPI api;
	
	public GameImpl(JsonObject object, Match match, OusuAPI api) {
		this.object = object;
		this.match = match;
		this.api = api;
	}

	public Match getMatch() {
		return match;
	}
	
	public long getGameId() {
		return object.get("game_id").getAsLong();
	}

	public long getBeatmapId() {
		return object.get("beatmap_id").getAsLong();
	}
	
	public Request<Beatmap> getBeatmap() {
		return api.getBeatmap(getBeatmapId());
	}

	public Gamemode getGamemode() {
		return Gamemode.getById(object.get("playmode").getAsInt());
	}

	public Scoring getScoreType() {
		return Scoring.getTeamById(object.get("scoring_type").getAsInt());
	}

	public Team getTeamType() {
		return Team.getTeamById(object.get("team_type").getAsInt());
	}

	public Mods[] getGlobalMods() {
		return Mods.get(object.get("mods").getAsLong());
	}

	public List<MultiplayerScore> getScores() {
		List<MultiplayerScore> score = new ArrayList<>();
		if (object.get("scores").isJsonNull()) {
			return score;
		}
		
		JsonArray array = object.get("scores").getAsJsonArray();

		if (array.size() == 0) {
			return score;
		}
		
		for (JsonElement ele :array) {
			JsonObject mp = ele.getAsJsonObject();
			score.add(new MultiplayerScoreImpl(mp, this, api));
		}
		
		return score;
	}
	
	private OffsetDateTime getDate(String parse) {
		if (object.get(parse).isJsonNull()){
			return null;
		}
		LocalDateTime time = LocalDateTime.parse(object.get(parse).getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return OffsetDateTime.of(time, ZoneOffset.UTC);
	}

	public OffsetDateTime getGameStartTime() {
		return getDate("start_time");
	}

	public OffsetDateTime getGameEndTime() {
		return getDate("end_time");
	}
	
	public String toString() {
		return "[gameId=" + getGameId() + ", beatmapId=" + getBeatmapId()+ ", gamemode=" + getGamemode() + "]";
	}
}
