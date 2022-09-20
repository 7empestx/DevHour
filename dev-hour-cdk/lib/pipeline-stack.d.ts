/**
 * Pipeline Stack
 * @version 0.9.0
 */
import { Construct } from 'constructs';
import { Stack, Stage } from 'aws-cdk-lib';
import { ShellStep } from 'aws-cdk-lib/pipelines';
export interface PipelineStackProps {
    account: string;
    region: string;
    id: string;
    stackId: string;
    shellStep: ShellStep;
    stages: Stage[];
}
export declare class PipelineStack extends Stack {
    private readonly pipeline;
    constructor(scope: Construct, props: PipelineStackProps);
}
