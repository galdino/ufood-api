package com.galdino.ufood.core.validation.security.authserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtKeyStoreProperties jwtKeyStoreProperties;
    private final DataSource dataSource;


    public AuthorizationServerConfig(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, RedisConnectionFactory redisConnectionFactory, JwtKeyStoreProperties jwtKeyStoreProperties, DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtKeyStoreProperties = jwtKeyStoreProperties;
        this.dataSource = dataSource;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients();

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                 .userDetailsService(userDetailsService)
                 .authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource))
                 .reuseRefreshTokens(false)
                 .accessTokenConverter(jwtAccessTokenConverter())
                 .tokenEnhancer(tokenEnhancerChain)
                 .approvalStore(approvalStore(endpoints.getTokenStore()))
                 .tokenGranter(tokenGranter(endpoints));
    }

    private ApprovalStore approvalStore(TokenStore tokenStore) {
        TokenApprovalStore approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        String keyStorePass = jwtKeyStoreProperties.getPassword();
        String keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);

        jwtAccessTokenConverter.setKeyPair(keyPair);

        return jwtAccessTokenConverter;
    }


    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }
}
