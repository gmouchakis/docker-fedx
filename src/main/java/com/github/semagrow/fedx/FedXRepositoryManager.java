package com.github.semagrow.fedx;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.impl.TreeModel;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.manager.RepositoryInfo;
import org.openrdf.repository.manager.RepositoryManager;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;

import com.fluidops.fedx.Config;
import com.fluidops.fedx.FedXFactory;
import com.fluidops.fedx.exception.FedXException;
import com.fluidops.fedx.structures.Endpoint;
import com.fluidops.fedx.util.EndpointFactory;

public class FedXRepositoryManager extends RepositoryManager {
	
	Logger log = Logger.getLogger(this.getClass());
	
	boolean isInitialized = false;
	SailRepository repository = null;

	@Override
	public Repository getRepository(String id) {
		
		try {
			List<Endpoint> members = loadFederationMembers(id);
			
			if (repository == null) {
				repository = FedXFactory.initializeFederation(members);
			} 

			return repository;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	private List<Endpoint> loadFederationMembers(String id) throws FedXException, Exception {
		String dataConfig = "/etc/fedx/federated-sparql.ttl";
		Model graph = new TreeModel();
		RDFParser parser = Rio.createParser(RDFFormat.TURTLE);
		RDFHandler handler = new DefaultRDFHandler(graph);
		parser.setRDFHandler(handler);
		parser.parse(new FileReader(dataConfig), "http://fluidops.org/config#");
		
		if ( ! isInitialized ) {
			Config.initialize(dataConfig);
			isInitialized = true;
		}
		
		
		List<Endpoint> res = new ArrayList<Endpoint>();
		for (Statement st : graph.filter(null, new URIImpl("http://fluidops.org/config#store"), null)) {
			Endpoint e = loadEndpoint(graph, st.getSubject(), st.getObject());
			res.add(e);
		}
				
		return res;
	}
	

	private  Endpoint loadEndpoint(Model graph, Resource repNode, Value repType) throws Exception {
		return EndpointFactory.loadEndpoint(graph, repNode, repType);
	}

	@Override
	protected void cleanUpRepository(String arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Repository createRepository(String arg0) throws RepositoryConfigException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Repository createSystemRepository() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<RepositoryInfo> getAllRepositoryInfos(boolean arg0) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public HttpClient getHttpClient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getLocation() throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepositoryInfo getRepositoryInfo(String arg0) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setHttpClient(HttpClient arg0) {
		// TODO Auto-generated method stub
		
	}


	protected static class DefaultRDFHandler implements RDFHandler {

		protected final Model graph;
				
		public DefaultRDFHandler(Model graph) {
			super();
			this.graph = graph;
		}

		public void endRDF() throws RDFHandlerException {
			; // no-op
		}
		
		public void handleComment(String comment) throws RDFHandlerException {
			; // no-op			
		}

		public void handleNamespace(String prefix, String uri)
				throws RDFHandlerException {
			; // no-op			
		}

		public void handleStatement(Statement st) throws RDFHandlerException {
			graph.add(st);			
		}

		public void startRDF() throws RDFHandlerException {
			; // no-op			
		}		
}
}
