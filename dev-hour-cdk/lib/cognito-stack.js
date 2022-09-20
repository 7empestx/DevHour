"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CognitoStack = void 0;
const aws_cognito_1 = require("@aws-cdk/aws-cognito");
const core_1 = require("@aws-cdk/core");
class CognitoStack extends core_1.Stack {
    constructor(scope, props) {
        super(scope, props.stackID, { env: {
                account: props.account,
                region: props.region
            } });
        this.userPool = new aws_cognito_1.UserPool(this, props.userPoolID, {
            selfSignUpEnabled: true,
            signInAliases: {
                username: true,
                email: true
            },
            standardAttributes: {
                fullname: {
                    required: true,
                    mutable: false
                }
            },
            passwordPolicy: {
                minLength: 8,
            },
        });
        this.userPoolIdentityProviderAmazon = new aws_cognito_1.UserPoolIdentityProviderAmazon(this, props.identityProviderID);
    }
}
exports.CognitoStack = CognitoStack;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29nbml0by1zdGFjay5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbImNvZ25pdG8tc3RhY2sudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7O0FBQUEsc0RBQTZGO0FBQzdGLHdDQUE4QztBQWE5QyxNQUFhLFlBQWEsU0FBUSxZQUFLO0lBTW5DLFlBQWEsS0FBZ0IsRUFBRSxLQUFtQjtRQUM5QyxLQUFLLENBQUMsS0FBSyxFQUFFLEtBQUssQ0FBQyxPQUFPLEVBQUUsRUFBRSxHQUFHLEVBQUU7Z0JBQy9CLE9BQU8sRUFBRSxLQUFLLENBQUMsT0FBTztnQkFDdEIsTUFBTSxFQUFFLEtBQUssQ0FBRSxNQUFNO2FBQ3hCLEVBQUMsQ0FBQyxDQUFDO1FBRUosSUFBSSxDQUFDLFFBQVEsR0FBRyxJQUFJLHNCQUFRLENBQUMsSUFBSSxFQUFFLEtBQUssQ0FBQyxVQUFVLEVBQUU7WUFDakQsaUJBQWlCLEVBQUUsSUFBSTtZQUN2QixhQUFhLEVBQUU7Z0JBQ1gsUUFBUSxFQUFFLElBQUk7Z0JBQ2QsS0FBSyxFQUFFLElBQUk7YUFDZDtZQUNELGtCQUFrQixFQUFFO2dCQUNoQixRQUFRLEVBQUU7b0JBQ04sUUFBUSxFQUFFLElBQUk7b0JBQ2QsT0FBTyxFQUFFLEtBQUs7aUJBQ2pCO2FBQ0o7WUFDRCxjQUFjLEVBQUU7Z0JBQ1osU0FBUyxFQUFFLENBQUM7YUFDZjtTQUNKLENBQUMsQ0FBQztRQUVILElBQUksQ0FBQyw4QkFBOEIsR0FBRyxJQUFJLDRDQUE4QixDQUFDLElBQUksRUFBRSxLQUFLLENBQUMsa0JBQWtCLENBQUUsQ0FBQztJQUM5RyxDQUFDO0NBQ0o7QUEvQkQsb0NBK0JDIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHtVc2VyUG9vbCwgVXNlclBvb2xDbGllbnQsIFVzZXJQb29sSWRlbnRpdHlQcm92aWRlckFtYXpvbn0gZnJvbSAnQGF3cy1jZGsvYXdzLWNvZ25pdG8nXG5pbXBvcnQge1N0YWNrLCBDb25zdHJ1Y3R9IGZyb20gJ0Bhd3MtY2RrL2NvcmUnIFxuaW1wb3J0IHtDb25zdGFudHN9IGZyb20gJy4vY29uc3RhbnRzJztcbmltcG9ydCB7SWRlbnRpdHlQb29sfSBmcm9tICdAYXdzLWNkay9hd3MtY29nbml0by1pZGVudGl0eXBvb2wnXG5cbmV4cG9ydCBpbnRlcmZhY2UgQ29nbml0b1Byb3BzIHtcbiAgICBhY2NvdW50ICAgICAgICA6IHN0cmluZyxcbiAgICByZWdpb24gICAgICAgICA6IHN0cmluZywgXG4gICAgdXNlclBvb2xJRCAgICAgOiBzdHJpbmcsIFxuICAgIGlkZW50aXR5UG9vbElEIDogc3RyaW5nLCBcbiAgICBzdGFja0lEICAgICAgICA6IHN0cmluZyxcbiAgICBpZGVudGl0eVByb3ZpZGVySUQgOiBzdHJpbmcsXG59XG5cbmV4cG9ydCBjbGFzcyBDb2duaXRvU3RhY2sgZXh0ZW5kcyBTdGFjayB7XG4gICAgcHJpdmF0ZSByZWFkb25seSB1c2VyUG9vbCA6IFVzZXJQb29sXG4gICAgcHJpdmF0ZSByZWFkb25seSBJZGVudGl0eSA6IElkZW50aXR5UG9vbFxuICAgIHByaXZhdGUgcmVhZG9ubHkgdXNlclBvb2xDbGllbnQgOiBVc2VyUG9vbENsaWVudFxuICAgIHByaXZhdGUgcmVhZG9ubHkgdXNlclBvb2xJZGVudGl0eVByb3ZpZGVyQW1hem9uIDogVXNlclBvb2xJZGVudGl0eVByb3ZpZGVyQW1hem9uXG5cbiAgICBjb25zdHJ1Y3RvciAoc2NvcGU6IENvbnN0cnVjdCwgcHJvcHM6IENvZ25pdG9Qcm9wcykge1xuICAgICAgICBzdXBlcihzY29wZSwgcHJvcHMuc3RhY2tJRCwgeyBlbnY6IHtcbiAgICAgICAgICAgIGFjY291bnQ6IHByb3BzLmFjY291bnQsXG4gICAgICAgICAgICByZWdpb246IHByb3BzLiByZWdpb25cbiAgICAgICAgfX0pO1xuXG4gICAgICAgIHRoaXMudXNlclBvb2wgPSBuZXcgVXNlclBvb2wodGhpcywgcHJvcHMudXNlclBvb2xJRCwge1xuICAgICAgICAgICAgc2VsZlNpZ25VcEVuYWJsZWQ6IHRydWUsXG4gICAgICAgICAgICBzaWduSW5BbGlhc2VzOiB7XG4gICAgICAgICAgICAgICAgdXNlcm5hbWU6IHRydWUsXG4gICAgICAgICAgICAgICAgZW1haWw6IHRydWVcbiAgICAgICAgICAgIH0sXG4gICAgICAgICAgICBzdGFuZGFyZEF0dHJpYnV0ZXM6IHtcbiAgICAgICAgICAgICAgICBmdWxsbmFtZToge1xuICAgICAgICAgICAgICAgICAgICByZXF1aXJlZDogdHJ1ZSxcbiAgICAgICAgICAgICAgICAgICAgbXV0YWJsZTogZmFsc2VcbiAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICB9LFxuICAgICAgICAgICAgcGFzc3dvcmRQb2xpY3k6IHtcbiAgICAgICAgICAgICAgICBtaW5MZW5ndGg6IDgsXG4gICAgICAgICAgICB9LFxuICAgICAgICB9KTtcbiAgICAgICAgXG4gICAgICAgIHRoaXMudXNlclBvb2xJZGVudGl0eVByb3ZpZGVyQW1hem9uID0gbmV3IFVzZXJQb29sSWRlbnRpdHlQcm92aWRlckFtYXpvbih0aGlzLCBwcm9wcy5pZGVudGl0eVByb3ZpZGVySUQsKTtcbiAgICB9XG59XG5cbiJdfQ==