@Bean
SecurityFilterChain adminSecurityFilterChain(HttpSecurity http)throws Exception{
    http.requestMatchers(requestMatchers->requestMatchers.mvcMatchers("/admin/**"))
    // TODO Other configs
    return http.build();
}

@Bean
SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
    http.requestMatchers(requestMatchers ->requestMatchers.mvcMatchers("/app/**"));
    // TODO Other configs
    return http.build();
}