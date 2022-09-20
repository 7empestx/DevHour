"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CognitoStack = void 0;
const aws_cognito_1 = require("aws-cdk-lib/aws-cognito");
const aws_cdk_lib_1 = require("aws-cdk-lib");
class CognitoStack extends aws_cdk_lib_1.Stack {
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
        this.userPoolClient = new aws_cognito_1.UserPoolClient(this, props.userPoolClientID, {
            userPool: this.userPool
        });
        this.userPoolIdentityProviderAmazon = new aws_cognito_1.UserPoolIdentityProviderAmazon(this, props.identityProviderID, {
            userPool: this.userPool,
            clientId: props.userPoolClientID,
            clientSecret: props.userPoolClientSecret
        });
        this.identityPool = new aws_cognito_1.CfnIdentityPool(this, props.identityPoolID, {
            allowUnauthenticatedIdentities: false
        });
    }
}
exports.CognitoStack = CognitoStack;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29nbml0by1zdGFjay5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbImNvZ25pdG8tc3RhY2sudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7O0FBQUEseURBQW1IO0FBQ25ILDZDQUFtQztBQWtCbkMsTUFBYSxZQUFhLFNBQVEsbUJBQUs7SUFNbkMsWUFBYSxLQUFnQixFQUFFLEtBQW1CO1FBQzlDLEtBQUssQ0FBQyxLQUFLLEVBQUUsS0FBSyxDQUFDLE9BQU8sRUFBRSxFQUFFLEdBQUcsRUFBRTtnQkFDL0IsT0FBTyxFQUFFLEtBQUssQ0FBQyxPQUFPO2dCQUN0QixNQUFNLEVBQUUsS0FBSyxDQUFFLE1BQU07YUFDeEIsRUFBQyxDQUFDLENBQUM7UUFFSixJQUFJLENBQUMsUUFBUSxHQUFHLElBQUksc0JBQVEsQ0FBQyxJQUFJLEVBQUUsS0FBSyxDQUFDLFVBQVUsRUFBRTtZQUNqRCxpQkFBaUIsRUFBRSxJQUFJO1lBQ3ZCLGFBQWEsRUFBRTtnQkFDWCxRQUFRLEVBQUUsSUFBSTtnQkFDZCxLQUFLLEVBQUUsSUFBSTthQUNkO1lBQ0Qsa0JBQWtCLEVBQUU7Z0JBQ2hCLFFBQVEsRUFBRTtvQkFDTixRQUFRLEVBQUUsSUFBSTtvQkFDZCxPQUFPLEVBQUUsS0FBSztpQkFDakI7YUFDSjtZQUNELGNBQWMsRUFBRTtnQkFDWixTQUFTLEVBQUUsQ0FBQzthQUNmO1NBQ0osQ0FBQyxDQUFDO1FBRUgsSUFBSSxDQUFDLGNBQWMsR0FBRyxJQUFJLDRCQUFjLENBQUMsSUFBSSxFQUFFLEtBQUssQ0FBQyxnQkFBZ0IsRUFBRTtZQUNuRSxRQUFRLEVBQUUsSUFBSSxDQUFDLFFBQVE7U0FDMUIsQ0FBQyxDQUFBO1FBRUYsSUFBSSxDQUFDLDhCQUE4QixHQUFHLElBQUksNENBQThCLENBQUMsSUFBSSxFQUFFLEtBQUssQ0FBQyxrQkFBa0IsRUFBRTtZQUNyRyxRQUFRLEVBQUUsSUFBSSxDQUFDLFFBQVE7WUFDdkIsUUFBUSxFQUFFLEtBQUssQ0FBQyxnQkFBZ0I7WUFDaEMsWUFBWSxFQUFFLEtBQUssQ0FBQyxvQkFBb0I7U0FDM0MsQ0FBQyxDQUFDO1FBQ0gsSUFBSSxDQUFDLFlBQVksR0FBRyxJQUFJLDZCQUFlLENBQUMsSUFBSSxFQUFFLEtBQUssQ0FBQyxjQUFjLEVBQUU7WUFDMUUsOEJBQThCLEVBQUUsS0FBSztTQUNyQyxDQUFDLENBQUM7SUFDQSxDQUFDO0NBQ0o7QUExQ0Qsb0NBMENDIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgQ2ZuSWRlbnRpdHlQb29sLCBVc2VyUG9vbCwgVXNlclBvb2xDbGllbnQsIFVzZXJQb29sSWRlbnRpdHlQcm92aWRlckFtYXpvbiB9IGZyb20gJ2F3cy1jZGstbGliL2F3cy1jb2duaXRvJ1xuaW1wb3J0IHsgU3RhY2sgfSBmcm9tICdhd3MtY2RrLWxpYidcbmltcG9ydCB7IENvbnN0cnVjdCB9IGZyb20gJ2NvbnN0cnVjdHMnXG5pbXBvcnQgeyBSb2xlIH0gZnJvbSAnYXdzLWNkay1saWIvYXdzLWlhbSc7XG5pbXBvcnQgeyBDb25zdGFudHMgfSBmcm9tICcuL2NvbnN0YW50cyc7XG5cbmV4cG9ydCBpbnRlcmZhY2UgQ29nbml0b1Byb3BzIHtcbiAgICBhY2NvdW50ICAgICAgICA6IHN0cmluZyxcbiAgICByZWdpb24gICAgICAgICA6IHN0cmluZywgXG4gICAgdXNlclBvb2xJRCAgICAgOiBzdHJpbmcsIFxuICAgIGlkZW50aXR5UG9vbElEIDogc3RyaW5nLCBcbiAgICBzdGFja0lEICAgICAgICA6IHN0cmluZyxcbiAgICBpZGVudGl0eVByb3ZpZGVySUQgOiBzdHJpbmcsXG4gICAgdXNlclBvb2xDbGllbnRJRCA6IHN0cmluZyxcbiAgICB1c2VyUG9vbENsaWVudFNlY3JldCA6IHN0cmluZyxcbiAgICBhdXRoZW50aWNhdGVkUm9sZTogUm9sZSxcbiAgICB1bmF1dGhlbnRpY2F0ZWRSb2xlOiBSb2xlLFxufVxuXG5leHBvcnQgY2xhc3MgQ29nbml0b1N0YWNrIGV4dGVuZHMgU3RhY2sge1xuICAgIHByaXZhdGUgcmVhZG9ubHkgdXNlclBvb2wgOiBVc2VyUG9vbFxuICAgIHByaXZhdGUgcmVhZG9ubHkgaWRlbnRpdHlQb29sIDogQ2ZuSWRlbnRpdHlQb29sXG4gICAgcHJpdmF0ZSByZWFkb25seSB1c2VyUG9vbENsaWVudCA6IFVzZXJQb29sQ2xpZW50XG4gICAgcHJpdmF0ZSByZWFkb25seSB1c2VyUG9vbElkZW50aXR5UHJvdmlkZXJBbWF6b24gOiBVc2VyUG9vbElkZW50aXR5UHJvdmlkZXJBbWF6b25cblxuICAgIGNvbnN0cnVjdG9yIChzY29wZTogQ29uc3RydWN0LCBwcm9wczogQ29nbml0b1Byb3BzKSB7XG4gICAgICAgIHN1cGVyKHNjb3BlLCBwcm9wcy5zdGFja0lELCB7IGVudjoge1xuICAgICAgICAgICAgYWNjb3VudDogcHJvcHMuYWNjb3VudCxcbiAgICAgICAgICAgIHJlZ2lvbjogcHJvcHMuIHJlZ2lvblxuICAgICAgICB9fSk7XG5cbiAgICAgICAgdGhpcy51c2VyUG9vbCA9IG5ldyBVc2VyUG9vbCh0aGlzLCBwcm9wcy51c2VyUG9vbElELCB7XG4gICAgICAgICAgICBzZWxmU2lnblVwRW5hYmxlZDogdHJ1ZSxcbiAgICAgICAgICAgIHNpZ25JbkFsaWFzZXM6IHtcbiAgICAgICAgICAgICAgICB1c2VybmFtZTogdHJ1ZSxcbiAgICAgICAgICAgICAgICBlbWFpbDogdHJ1ZVxuICAgICAgICAgICAgfSxcbiAgICAgICAgICAgIHN0YW5kYXJkQXR0cmlidXRlczoge1xuICAgICAgICAgICAgICAgIGZ1bGxuYW1lOiB7XG4gICAgICAgICAgICAgICAgICAgIHJlcXVpcmVkOiB0cnVlLFxuICAgICAgICAgICAgICAgICAgICBtdXRhYmxlOiBmYWxzZVxuICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgIH0sXG4gICAgICAgICAgICBwYXNzd29yZFBvbGljeToge1xuICAgICAgICAgICAgICAgIG1pbkxlbmd0aDogOCxcbiAgICAgICAgICAgIH0sXG4gICAgICAgIH0pO1xuICAgICAgICBcbiAgICAgICAgdGhpcy51c2VyUG9vbENsaWVudCA9IG5ldyBVc2VyUG9vbENsaWVudCh0aGlzLCBwcm9wcy51c2VyUG9vbENsaWVudElELCB7XG4gICAgICAgICAgICB1c2VyUG9vbDogdGhpcy51c2VyUG9vbFxuICAgICAgICB9KVxuXG4gICAgICAgIHRoaXMudXNlclBvb2xJZGVudGl0eVByb3ZpZGVyQW1hem9uID0gbmV3IFVzZXJQb29sSWRlbnRpdHlQcm92aWRlckFtYXpvbih0aGlzLCBwcm9wcy5pZGVudGl0eVByb3ZpZGVySUQsIHtcbiAgICAgICAgICAgIHVzZXJQb29sOiB0aGlzLnVzZXJQb29sLFxuICAgICAgICAgICAgY2xpZW50SWQ6IHByb3BzLnVzZXJQb29sQ2xpZW50SUQsXG4gICAgICAgICAgICBjbGllbnRTZWNyZXQ6IHByb3BzLnVzZXJQb29sQ2xpZW50U2VjcmV0XG4gICAgICAgIH0pO1xuICAgICAgICB0aGlzLmlkZW50aXR5UG9vbCA9IG5ldyBDZm5JZGVudGl0eVBvb2wodGhpcywgcHJvcHMuaWRlbnRpdHlQb29sSUQsIHtcblx0XHRhbGxvd1VuYXV0aGVudGljYXRlZElkZW50aXRpZXM6IGZhbHNlXG5cdH0pO1xuICAgIH1cbn1cblxuIl19