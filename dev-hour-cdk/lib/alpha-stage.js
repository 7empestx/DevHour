"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.AlphaStage = void 0;
/**
 * Alpha Stage. Defines the resources for the Alpha Stage
 * @version 0.1.0
 */
const core_1 = require("@aws-cdk/core");
/// -------------------------
/// AlphaStage Implementation
class AlphaStage extends core_1.Stage {
    /// ---------------
    /// Private Members
    /// -----------
    /// Constructor
    constructor(scope, props) {
        super(scope, props.stageId, { env: {
                account: props.account,
                region: props.region
            } });
    }
}
exports.AlphaStage = AlphaStage;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYWxwaGEtc3RhZ2UuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJhbHBoYS1zdGFnZS50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7QUFBQTs7O0dBR0c7QUFDSCx3Q0FBZ0Q7QUFhaEQsNkJBQTZCO0FBQzdCLDZCQUE2QjtBQUU3QixNQUFhLFVBQVcsU0FBUSxZQUFLO0lBRWpDLG1CQUFtQjtJQUNuQixtQkFBbUI7SUFFbkIsZUFBZTtJQUNmLGVBQWU7SUFFZixZQUFZLEtBQWdCLEVBQUUsS0FBc0I7UUFDaEQsS0FBSyxDQUFDLEtBQUssRUFBRSxLQUFLLENBQUMsT0FBTyxFQUFFLEVBQUUsR0FBRyxFQUFFO2dCQUMvQixPQUFPLEVBQUssS0FBSyxDQUFDLE9BQU87Z0JBQ3pCLE1BQU0sRUFBTSxLQUFLLENBQUMsTUFBTTthQUMzQixFQUFDLENBQUMsQ0FBQztJQUVSLENBQUM7Q0FFSjtBQWhCRCxnQ0FnQkMiLCJzb3VyY2VzQ29udGVudCI6WyIvKipcbiAqIEFscGhhIFN0YWdlLiBEZWZpbmVzIHRoZSByZXNvdXJjZXMgZm9yIHRoZSBBbHBoYSBTdGFnZVxuICogQHZlcnNpb24gMC4xLjBcbiAqL1xuaW1wb3J0IHsgQ29uc3RydWN0LCBTdGFnZSB9IGZyb20gJ0Bhd3MtY2RrL2NvcmUnXG5pbXBvcnQgeyBDb25zdGFudHMgfSBmcm9tICcuL2NvbnN0YW50cydcblxuLy8vIC0tLS0tLS0tLS0tLS0tLS1cbi8vLyBBbHBoYVN0YWdlIFByb3BzXG5cbmV4cG9ydCBpbnRlcmZhY2UgQWxwaGFTdGFnZVByb3BzIHtcbiAgICBhY2NvdW50OiAgIHN0cmluZyxcbiAgICByZWdpb246ICAgIHN0cmluZyxcbiAgICBzdGFnZUlkOiAgIHN0cmluZyxcbiAgICBzdGFnZU5hbWU6IHN0cmluZ1xufVxuXG4vLy8gLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLVxuLy8vIEFscGhhU3RhZ2UgSW1wbGVtZW50YXRpb25cblxuZXhwb3J0IGNsYXNzIEFscGhhU3RhZ2UgZXh0ZW5kcyBTdGFnZSB7XG5cbiAgICAvLy8gLS0tLS0tLS0tLS0tLS0tXG4gICAgLy8vIFByaXZhdGUgTWVtYmVyc1xuXG4gICAgLy8vIC0tLS0tLS0tLS0tXG4gICAgLy8vIENvbnN0cnVjdG9yXG5cbiAgICBjb25zdHJ1Y3RvcihzY29wZTogQ29uc3RydWN0LCBwcm9wczogQWxwaGFTdGFnZVByb3BzKSB7XG4gICAgICAgIHN1cGVyKHNjb3BlLCBwcm9wcy5zdGFnZUlkLCB7IGVudjoge1xuICAgICAgICAgICAgYWNjb3VudDogICAgcHJvcHMuYWNjb3VudCxcbiAgICAgICAgICAgIHJlZ2lvbjogICAgIHByb3BzLnJlZ2lvblxuICAgICAgICB9fSk7XG5cbiAgICB9XG5cbn1cbiJdfQ==