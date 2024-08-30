package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.*;

public class TestDataBuild {

	
	
	public AddPlace addPlacePayLoad(String name, String language, String address)
	{
		AddPlace p =new AddPlace();
		p.setAccuracy(50);
		p.setAddress(address);
		p.setLanguage(language);
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName(name);
		List<String> myList =new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");

		p.setTypes(myList);
		Location l =new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		return p;
	}

	public Issue addDefectPayLoad(String summary, String name)
	{
		Issue issue=new Issue();
		Fields fields=new Fields();
		fields.setSummary(summary);
		Project project= new Project();
		project.setKey("SCRUM");
		IssueType issueType=new IssueType();
		issueType.setName(name);

		fields.setProject(project);
		fields.setIssuetype(issueType);

		issue.setFields(fields);

		return issue;

	}
	
	public String deletePlacePayload(String placeId)
	{
		return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
	}
}
