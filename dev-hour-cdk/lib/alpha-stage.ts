/**
 * Alpha Stage. Defines the resources for the Alpha Stage
 * @version 0.1.0
 */
import { Construct } from 'constructs'
import { Stage } from 'aws-cdk-lib'
import { Constants } from './constants'

/// ----------------
/// AlphaStage Props

export interface AlphaStageProps {
    account:   string,
    region:    string,
    stageId:   string,
    stageName: string
}

/// -------------------------
/// AlphaStage Implementation

export class AlphaStage extends Stage {

    /// ---------------
    /// Private Members

    /// -----------
    /// Constructor

    constructor(scope: Construct, props: AlphaStageProps) {
        super(scope, props.stageId, { env: {
            account:    props.account,
            region:     props.region
        }});

    }

}
