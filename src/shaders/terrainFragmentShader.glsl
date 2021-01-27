#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;
in vec4 shadowCoords;

out vec4 out_Color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform sampler2D shadowMap;

uniform vec3 lightColor[4];
uniform vec3 attenuation[4];
uniform vec3 skyColor;

uniform float shineDamper;
uniform float reflectivity;

uniform int useCelShading;
uniform float levels;

uniform float shadowMapSize;
uniform int pcfCount;

void main(void) {

	float totalTexels = (pcfCount * 2.0 + 1.0) * (pcfCount * 2.0 + 1.0);
	float texelSize = 1.0 / shadowMapSize;
	float total = 0.0;

	for (int i = -pcfCount; i <= pcfCount; ++i) {
		for (int j = -pcfCount; j <= pcfCount; ++j) {
			float objectNearestLight = texture(shadowMap, shadowCoords.xy + vec2(i, j) * texelSize).r;
			if (shadowCoords.z > objectNearestLight) {
					total += 1.0;
			}
		}
	}

	total /= totalTexels;

	float lightFactor = 1.0 - (total * shadowCoords.w);

    vec4 blendMapColor = texture(blendMap, pass_textureCoordinates);

    float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    vec2 tiledCoords = pass_textureCoordinates * 40.0;
    vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
    vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
    vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;

    vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for (int i = 0; i < 4; ++i) {
        float distance = length(toLightVector[i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
	    vec3 unitLightVector = normalize(toLightVector[i]);
	    float nDotl = dot(unitNormal, unitLightVector);
	    float brightness = max(nDotl, 0.0);
	    
	    // cel shading effect
    	float level;
    	if (useCelShading == 1) {
    		level = floor(brightness * levels);
    		brightness = level / levels;
    	}
    	
	    vec3 lightDirection = -unitLightVector;
	    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
	    specularFactor = max(specularFactor, 0.0);
	    float dampedFactor = pow(specularFactor, shineDamper);
	    
	    // cel shading effect
    	if (useCelShading == 1) {
    		level = floor(dampedFactor * levels);
    		dampedFactor = level / levels;
    	}
    	
	    totalDiffuse += (brightness * lightColor[i]) / attFactor;
	    totalSpecular += (dampedFactor * reflectivity * lightColor[i]) / attFactor;
    }
    totalDiffuse = max(totalDiffuse * lightFactor, 0.2);

	out_Color =  vec4(totalDiffuse, 1.0) * totalColor + vec4(totalSpecular, 1.0);
	out_Color = mix(vec4(skyColor, 1.0),out_Color, visibility);

}
