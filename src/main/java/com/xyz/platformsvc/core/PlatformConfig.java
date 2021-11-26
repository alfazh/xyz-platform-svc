package com.xyz.platformsvc.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.xyz.dal, com.xyz.platformsvc"})
public class PlatformConfig {
	
}
