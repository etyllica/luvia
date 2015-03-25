package br.com.luvia.loader.gis;

import java.io.File;
import java.io.IOException;

import br.com.luvia.gis.GISInfo;

public interface GISFileLoader {
	public GISInfo loadGISInfo(File file) throws IOException;
}
