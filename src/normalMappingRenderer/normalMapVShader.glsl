#version 400 core

#define MAX_LIGHTS 21

in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;
in vec3 tangent;

out vec2 pass_textureCoordinates;
out vec3 toLightVector[MAX_LIGHTS];
out vec3 toCameraVector;
out float visibility;
out vec4 shadowCoords;
out float pass_numberOfActiveLights;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPositionEyeSpace[MAX_LIGHTS];

uniform float numberOfRows;
uniform vec2 offset;

uniform float density;
uniform float gradient;

uniform vec4 plane;

uniform mat4 toShadowMapSpace;

uniform float shadowDistance;
uniform float transitionDistance;

uniform float numberOfActiveLights;

void main(void) {
	pass_numberOfActiveLights = numberOfActiveLights;

	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	shadowCoords = toShadowMapSpace * worldPosition;

	gl_ClipDistance[0] = dot(worldPosition, plane);
	mat4 modelViewMatrix = viewMatrix * transformationMatrix;
	vec4 positionRelativeToCam = modelViewMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * positionRelativeToCam;
	
	pass_textureCoordinates = (textureCoordinates / numberOfRows) + offset;
	
	vec3 surfaceNormal = (modelViewMatrix * vec4(normal, 0.0)).xyz;
	
	vec3 norm = normalize(surfaceNormal);
	vec3 tang = normalize((modelViewMatrix * vec4(tangent, 0.0)).xyz);
	vec3 bitang = normalize(cross(norm, tang));
	
	mat3 toTangentSpace = mat3(
		tang.x, bitang.x, norm.x,
		tang.y, bitang.y, norm.y,
		tang.z, bitang.z, norm.z
	);
	
	for (int i = 0; i < numberOfActiveLights; i++){
		toLightVector[i] = toTangentSpace * (lightPositionEyeSpace[i] - positionRelativeToCam.xyz);
	}
	toCameraVector = toTangentSpace * (-positionRelativeToCam.xyz);
	
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
	
	distance -= (shadowDistance - transitionDistance);
	distance /= transitionDistance;
	shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
}
