/**
 * Pipeline stack. Defines the pipeline for the CDK.
 * @version 0.1.0
 */
import { Construct } from 'constructs'
import { Stack, Stage } from 'aws-cdk-lib'
import { CodePipeline, ShellStep } from 'aws-cdk-lib/pipelines'
import { Constants } from './constants'

/// ----------
/// Properties

export interface PipelineStackProps {
    account:    string,
    region:     string,
    stackId:    string,
    pipelineId: string,
    shellStep:  ShellStep,
    stages:     Stage[]
}

/// --------------------
/// Class Implementation

export class PipelineStack extends Stack {

    /// ---------------
    /// Private Members

    private readonly pipeline: CodePipeline;

    /// -----------
    /// Constructor

    constructor(scope: Construct, props: PipelineStackProps) {
        super(scope, props.stackId, { env: {
            account: props.account,
            region:  props.region
        }});

        this.pipeline = new CodePipeline(this, props.pipelineId, {
            selfMutation: Constants.CodePipeline.SelfMutate,
            synth:        props.shellStep
        });

        props.stages.forEach( (stage) => {
            this.pipeline.addStage(stage);
        });

    }

}
