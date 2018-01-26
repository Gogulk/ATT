/**
* Dev space by Gogul... 
**/
package com.issue.git;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/")
public class GitController {

	RestTemplate restTemplate = restTemplate();

	String url = "https://api.github.com/repos/:owner/:repo/issues";

	List<Issues> getIssue(String repo) {
		ParameterizedTypeReference<List<Issues>> myBean = new ParameterizedTypeReference<List<Issues>>() {
		};
		ResponseEntity<List<Issues>> response = restTemplate.exchange(getRepoUrl(repo), HttpMethod.GET, null, myBean);
		List<Issues> resources = response.getBody();
		return resources;
	}

	private String getRepoUrl(String repo) {
		return "https://api.github.com/repos/att/" + repo + "/issues";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{repo}")
	List<Issues> getAllIssues(@PathVariable String repo) {
		List<Issues> issuesList = getIssue(repo);
		return issuesList;
	}

	private RestTemplate restTemplate() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new Jackson2HalModule());
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		// converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);
		return new RestTemplate(Arrays.asList(converter));
	}

	// public static void main(String[] args) {
	// GitController g = new GitController();
	// g.getIssue("rcloud");
	// }
}
