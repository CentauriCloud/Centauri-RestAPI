package org.centauri.cloud.rest.util;

import org.apache.commons.io.FileUtils;
import org.centauri.cloud.cloud.download.DownloadUtil;
import org.centauri.cloud.rest.RestAPI;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ApplicationPreparer {

	public void checkSwagger() {
		File file = new File(RestAPI.getInstance().getModuleDirectory().getPath() + "/swagger/");
		if (!file.exists() || !file.isDirectory()) {
			File zip = new File(RestAPI.getInstance().getModuleDirectory(), "swagger.zip");
			DownloadUtil.download("https://centauricloud.net/downloads/latest/swagger.zip", zip);
			ZipUtil.unpack(zip, file);
			FileUtils.deleteQuietly(zip);
		}
	}

	public void checkConfig() {
		File file = new File(RestAPI.getInstance().getModuleDirectory().getPath() + "/config.yml");
		if (!file.exists()) {
			try {
				Files.copy(this.getClass().getResourceAsStream("/config.yml"), file.toPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
