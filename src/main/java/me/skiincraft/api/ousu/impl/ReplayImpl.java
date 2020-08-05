package me.skiincraft.api.ousu.impl;

import java.util.Base64;

import com.google.gson.JsonObject;

import me.skiincraft.api.ousu.OusuAPI;
import me.skiincraft.api.ousu.Request;
import me.skiincraft.api.ousu.entity.beatmap.Beatmap;
import me.skiincraft.api.ousu.entity.replay.Replay;

public class ReplayImpl implements Replay {

	private OusuAPI api;
	private JsonObject object;
	private long beatmapid;
	
	public ReplayImpl(JsonObject object, long beatmapid, OusuAPI api) {
		this.object = object;
		this.api = api;
		this.beatmapid = beatmapid;
	}
	
	public Request<Beatmap> getBeatmap() {
		return api.getBeatmap(beatmapid);
	}

	public long getBeatmapId() {
		return beatmapid;
	}

	public String getBase64String() {
		return object.get("content").getAsString();
	}

	public String getEncodeType() {
		return object.get("encoding").getAsString();
	}

	public byte[] getDecode() {
		return Base64.getDecoder().decode(getBase64String());
	}

}