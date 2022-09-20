/**
 * App entrypoint.
 * @version 0.1.0
 */

import { App } from 'aws-cdk-lib/core'
import { ShellStep } from 'aws-cdk-lib/pipelines'
import { PipelineStack, PipelineStackProps } from '../lib/pipeline-stack'
import { AlphaStage } from '../lib/alpha-stage'
import { Constants } from '../lib/constants'

const app = new App();

const stages = [
    new AlphaStage(app, {
        account:   Constants.Account,
        region:    Constants.Region,
        stageId:   Constants.Stages.Alpha.Id,
        stageName: Constants.Stages.Alpha.Name
    })
];

new PipelineStack(app, {
    account:    Constants.Account,
    region:     Constants.Region,
    stackId:    Constants.CodePipeline.StackId,
    pipelineId: Constants.CodePipeline.Id,
    shellStep:  new ShellStep(Constants.CodePipeline.ShellStep.Id, {
        commands: []
    }),
    stages:     stages
});

app.synth()
