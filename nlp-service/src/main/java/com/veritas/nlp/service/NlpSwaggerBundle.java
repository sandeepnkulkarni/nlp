package com.veritas.nlp.service;

import io.dropwizard.setup.Environment;
import in.vectorpro.dropwizard.swagger.ConfigurationHelper;
import in.vectorpro.dropwizard.swagger.SwaggerBundle;
import in.vectorpro.dropwizard.swagger.SwaggerBundleConfiguration;

import org.apache.commons.lang3.StringUtils;

import com.veritas.nlp.resources.ApiRoot;

class NlpSwaggerBundle extends SwaggerBundle<NlpConfiguration> {
    @Override
    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(NlpConfiguration configuration) {
        return configuration.getSwaggerBundleConfiguration();
    }

    @Override
    public void run(NlpConfiguration configuration, Environment environment) throws Exception {
        SwaggerBundleConfiguration swaggerBundleConfiguration = getSwaggerBundleConfiguration(configuration);
        // Only add swagger if it's configured
        if (swaggerBundleConfiguration.isEnabled() && StringUtils.isNotBlank(swaggerBundleConfiguration.getResourcePackage())) {
            super.run(configuration, environment);
            ConfigurationHelper configurationHelper = new ConfigurationHelper(configuration, swaggerBundleConfiguration);

            // Also expose swagger at the API root in addition to the "/swagger" path
            ApiRoot.configureSwagger(configurationHelper.getUrlPattern(),
                    swaggerBundleConfiguration.getSwaggerViewConfiguration());
        }
    }
}
