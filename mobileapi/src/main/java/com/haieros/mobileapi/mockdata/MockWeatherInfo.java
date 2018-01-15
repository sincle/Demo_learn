package com.haieros.mobileapi.mockdata;

public class MockWeatherInfo extends MockService {
	@Override
	public String getJsonData() {
		WeatherInfo weather = new WeatherInfo();
		weather.setCity("Beijing");
		weather.setCityid("10000");

		Response response = getSuccessResponse();		
		response.setResult(JSON.toJSONString(weather));
		return JSON.toJSONString(response);
	}
}
