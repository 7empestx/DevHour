"use strict";
/**
 * Pipeline stack. Defines the pipeline for the CDK.
 * @version 0.1.0
 */
Object.defineProperty(exports, "__esModule", { value: true });
exports.PipelineStack = void 0;
const core_1 = require("@aws-cdk/core");
const pipelines_1 = require("@aws-cdk/pipelines");
const constants_1 = require("./constants");
/// --------------------
/// Class Implementation
class PipelineStack extends core_1.Stack {
    /// -----------
    /// Constructor
    constructor(scope, props) {
        super(scope, props.stackId, { env: {
                account: props.account,
                region: props.region
            } });
        this.pipeline = new pipelines_1.CodePipeline(this, props.pipelineId, {
            selfMutation: constants_1.Constants.CodePipeline.SelfMutate,
            synth: props.shellStep
        });
        props.stages.forEach((stage) => {
            this.pipeline.addStage(stage);
        });
    }
}
exports.PipelineStack = PipelineStack;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicGlwZWxpbmUtc3RhY2suanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJwaXBlbGluZS1zdGFjay50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiO0FBQUE7OztHQUdHOzs7QUFFSCx3Q0FBdUQ7QUFDdkQsa0RBQTREO0FBQzVELDJDQUF1QztBQWN2Qyx3QkFBd0I7QUFDeEIsd0JBQXdCO0FBRXhCLE1BQWEsYUFBYyxTQUFRLFlBQUs7SUFPcEMsZUFBZTtJQUNmLGVBQWU7SUFFZixZQUFZLEtBQWdCLEVBQUUsS0FBeUI7UUFDbkQsS0FBSyxDQUFDLEtBQUssRUFBRSxLQUFLLENBQUMsT0FBTyxFQUFFLEVBQUUsR0FBRyxFQUFFO2dCQUMvQixPQUFPLEVBQUUsS0FBSyxDQUFDLE9BQU87Z0JBQ3RCLE1BQU0sRUFBRyxLQUFLLENBQUMsTUFBTTthQUN4QixFQUFDLENBQUMsQ0FBQztRQUVKLElBQUksQ0FBQyxRQUFRLEdBQUcsSUFBSSx3QkFBWSxDQUFDLElBQUksRUFBRSxLQUFLLENBQUMsVUFBVSxFQUFFO1lBQ3JELFlBQVksRUFBRSxxQkFBUyxDQUFDLFlBQVksQ0FBQyxVQUFVO1lBQy9DLEtBQUssRUFBUyxLQUFLLENBQUMsU0FBUztTQUNoQyxDQUFDLENBQUM7UUFFSCxLQUFLLENBQUMsTUFBTSxDQUFDLE9BQU8sQ0FBRSxDQUFDLEtBQUssRUFBRSxFQUFFO1lBQzVCLElBQUksQ0FBQyxRQUFRLENBQUMsUUFBUSxDQUFDLEtBQUssQ0FBQyxDQUFDO1FBQ2xDLENBQUMsQ0FBQyxDQUFDO0lBRVAsQ0FBQztDQUVKO0FBM0JELHNDQTJCQyIsInNvdXJjZXNDb250ZW50IjpbIi8qKlxuICogUGlwZWxpbmUgc3RhY2suIERlZmluZXMgdGhlIHBpcGVsaW5lIGZvciB0aGUgQ0RLLlxuICogQHZlcnNpb24gMC4xLjBcbiAqL1xuXG5pbXBvcnQgeyBDb25zdHJ1Y3QsIFN0YWNrLCBTdGFnZSB9IGZyb20gJ0Bhd3MtY2RrL2NvcmUnXG5pbXBvcnQgeyBDb2RlUGlwZWxpbmUsIFNoZWxsU3RlcCB9IGZyb20gJ0Bhd3MtY2RrL3BpcGVsaW5lcydcbmltcG9ydCB7IENvbnN0YW50cyB9IGZyb20gJy4vY29uc3RhbnRzJ1xuXG4vLy8gLS0tLS0tLS0tLVxuLy8vIFByb3BlcnRpZXNcblxuZXhwb3J0IGludGVyZmFjZSBQaXBlbGluZVN0YWNrUHJvcHMge1xuICAgIGFjY291bnQ6ICAgIHN0cmluZyxcbiAgICByZWdpb246ICAgICBzdHJpbmcsXG4gICAgc3RhY2tJZDogICAgc3RyaW5nLFxuICAgIHBpcGVsaW5lSWQ6IHN0cmluZyxcbiAgICBzaGVsbFN0ZXA6ICBTaGVsbFN0ZXAsXG4gICAgc3RhZ2VzOiAgICAgU3RhZ2VbXVxufVxuXG4vLy8gLS0tLS0tLS0tLS0tLS0tLS0tLS1cbi8vLyBDbGFzcyBJbXBsZW1lbnRhdGlvblxuXG5leHBvcnQgY2xhc3MgUGlwZWxpbmVTdGFjayBleHRlbmRzIFN0YWNrIHtcblxuICAgIC8vLyAtLS0tLS0tLS0tLS0tLS1cbiAgICAvLy8gUHJpdmF0ZSBNZW1iZXJzXG5cbiAgICBwcml2YXRlIHJlYWRvbmx5IHBpcGVsaW5lOiBDb2RlUGlwZWxpbmU7XG5cbiAgICAvLy8gLS0tLS0tLS0tLS1cbiAgICAvLy8gQ29uc3RydWN0b3JcblxuICAgIGNvbnN0cnVjdG9yKHNjb3BlOiBDb25zdHJ1Y3QsIHByb3BzOiBQaXBlbGluZVN0YWNrUHJvcHMpIHtcbiAgICAgICAgc3VwZXIoc2NvcGUsIHByb3BzLnN0YWNrSWQsIHsgZW52OiB7XG4gICAgICAgICAgICBhY2NvdW50OiBwcm9wcy5hY2NvdW50LFxuICAgICAgICAgICAgcmVnaW9uOiAgcHJvcHMucmVnaW9uXG4gICAgICAgIH19KTtcblxuICAgICAgICB0aGlzLnBpcGVsaW5lID0gbmV3IENvZGVQaXBlbGluZSh0aGlzLCBwcm9wcy5waXBlbGluZUlkLCB7XG4gICAgICAgICAgICBzZWxmTXV0YXRpb246IENvbnN0YW50cy5Db2RlUGlwZWxpbmUuU2VsZk11dGF0ZSxcbiAgICAgICAgICAgIHN5bnRoOiAgICAgICAgcHJvcHMuc2hlbGxTdGVwXG4gICAgICAgIH0pO1xuXG4gICAgICAgIHByb3BzLnN0YWdlcy5mb3JFYWNoKCAoc3RhZ2UpID0+IHtcbiAgICAgICAgICAgIHRoaXMucGlwZWxpbmUuYWRkU3RhZ2Uoc3RhZ2UpO1xuICAgICAgICB9KTtcblxuICAgIH1cblxufVxuIl19