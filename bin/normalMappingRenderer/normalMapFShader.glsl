#version 400 core

#define MAX_LIGHTS 21

in vec2 pass_textureCoordinates;
in vec3 toLightVector[MAX_LIGHTS];
in vec3 toCameraVector;
in float visibility;
in vec4 shadowCoords;
in float pass_numberOfActiveLights;

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform sampler2D normalMap;
uniform sampler2D shadowMap;

uniform vec3 lightColour[MAX_LIGHTS];
uniform vec3 attenuation[MAX_LIGHTS];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

uniform float shadowMapSize;
uniform int pcfCount;

void main(void) {
	float totalTexels = (pcfCount * 2.0 + 1.0) * (pcfCount * 2.0 + 1.0);
	float texelSize = 1.0 / shadowMapSize;
	float total = 0.0;

	for (int i = -pcfCount; i <= pcfCount; ++i) {
		for (int j = -pcfCount; j <= pcfCount; ++j) {
			float objectNearestLight = texture(shadowMap, shadowCoords.xy + vec2(i, j) * texelSize).r;
			if (shadowCoords.z > objectNearestLight + 0.002) {
					total += 1.0;
			}
		}
	}

	total /= totalTexels;

	float lightFactor = 1.0 - (total * shadowCoords.w);

	vec4 normalMapValue = 2.0 * texture(normalMap, pass_textureCoordinates) - 1;

	vec3 unitNormal = normalize(normalMapValue.rgb);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	for (int i = 0; i < pass_numberOfActiveLights; i++){
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);	
		float nDotl = dot(unitNormal, unitLightVector);
		float brightness = max(nDotl, 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDiffuse = totalDiffuse + (brightness * lightColour[i]) / attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i]) / attFactor;
	}
	totalDiffuse = max(totalDiffuse * lightFactor, 0.2);
	
	vec4 textureColour = texture(modelTexture,pass_textureCoordinates);
	if(textureColour.a < 0.5){
		discard;
	}

	out_Color =  vec4(totalDiffuse, 1.0) * textureColour + vec4(totalSpecular, 1.0);
	out_Color = mix(vec4(skyColour, 1.0), out_Color, visibility);
}
