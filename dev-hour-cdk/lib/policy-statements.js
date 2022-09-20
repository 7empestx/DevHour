"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PolicyStatements = void 0;
const aws_iam_1 = require("aws-cdk-lib/aws-iam");
const constants_1 = require("./constants");
var PolicyStatements;
(function (PolicyStatements) {
    let DynamoDB;
    (function (DynamoDB) {
        class DynamoDBPolicyStatement extends aws_iam_1.PolicyStatement {
            constructor(resourceArns) {
                super({
                    effect: aws_iam_1.Effect.ALLOW,
                    actions: constants_1.Constants.DynamoDB.User.AllowActions,
                    resources: resourceArns
                });
            }
        }
        DynamoDB.DynamoDBPolicyStatement = DynamoDBPolicyStatement;
    })(DynamoDB = PolicyStatements.DynamoDB || (PolicyStatements.DynamoDB = {}));
})(PolicyStatements = exports.PolicyStatements || (exports.PolicyStatements = {}));
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicG9saWN5LXN0YXRlbWVudHMuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJwb2xpY3ktc3RhdGVtZW50cy50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7QUFDQSxpREFBNkQ7QUFDN0QsMkNBQXVDO0FBR3ZDLElBQWMsZ0JBQWdCLENBa0I3QjtBQWxCRCxXQUFjLGdCQUFnQjtJQUUxQixJQUFjLFFBQVEsQ0FjckI7SUFkRCxXQUFjLFFBQVE7UUFFbEIsTUFBYSx1QkFBd0IsU0FBUSx5QkFBZTtZQUV4RCxZQUFZLFlBQXNCO2dCQUM5QixLQUFLLENBQUM7b0JBQ0YsTUFBTSxFQUFFLGdCQUFNLENBQUMsS0FBSztvQkFDaEMsT0FBTyxFQUFFLHFCQUFTLENBQUMsUUFBUSxDQUFDLElBQUksQ0FBQyxZQUFZO29CQUNqQyxTQUFTLEVBQUUsWUFBWTtpQkFDMUIsQ0FBQyxDQUFDO1lBQ1AsQ0FBQztTQUVKO1FBVlksZ0NBQXVCLDBCQVVuQyxDQUFBO0lBRUwsQ0FBQyxFQWRhLFFBQVEsR0FBUix5QkFBUSxLQUFSLHlCQUFRLFFBY3JCO0FBRUwsQ0FBQyxFQWxCYSxnQkFBZ0IsR0FBaEIsd0JBQWdCLEtBQWhCLHdCQUFnQixRQWtCN0IiLCJzb3VyY2VzQ29udGVudCI6WyJcbmltcG9ydCB7IEVmZmVjdCwgUG9saWN5U3RhdGVtZW50IH0gZnJvbSAnYXdzLWNkay1saWIvYXdzLWlhbSdcbmltcG9ydCB7IENvbnN0YW50cyB9IGZyb20gJy4vY29uc3RhbnRzJ1xuXG5cbmV4cG9ydCBtb2R1bGUgUG9saWN5U3RhdGVtZW50cyB7XG5cbiAgICBleHBvcnQgbW9kdWxlIER5bmFtb0RCIHtcblxuICAgICAgICBleHBvcnQgY2xhc3MgRHluYW1vREJQb2xpY3lTdGF0ZW1lbnQgZXh0ZW5kcyBQb2xpY3lTdGF0ZW1lbnQge1xuXG4gICAgICAgICAgICBjb25zdHJ1Y3RvcihyZXNvdXJjZUFybnM6IHN0cmluZ1tdKSB7XG4gICAgICAgICAgICAgICAgc3VwZXIoe1xuICAgICAgICAgICAgICAgICAgICBlZmZlY3Q6IEVmZmVjdC5BTExPVyxcbiAgXHRcdCAgICBhY3Rpb25zOiBDb25zdGFudHMuRHluYW1vREIuVXNlci5BbGxvd0FjdGlvbnMsXG4gICAgICAgICAgICAgICAgICAgIHJlc291cmNlczogcmVzb3VyY2VBcm5zXG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgfVxuXG4gICAgfVxuXG59XG4iXX0=