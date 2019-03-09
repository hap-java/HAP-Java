package com.beowulfe.hap.accessories;

/**
 * A window covering, like blinds, which can be remotely controlled.
 *
 * @author Andy Lintner
 * @deprecated In 1.2.x, this interface will become replaced with BasicWindowCovering. Update your
 *     code to use that interface for now, and include the HoldPositionWindowCovering and
 *     ObstructionDetectedWindowCovering interfaces respectively
 */
@Deprecated
public interface WindowCovering
    extends BasicWindowCovering, HoldPositionWindowCovering, ObstructionDetectedWindowCovering {
  /*
   * TODO - 1.2.x: Replace this interface with BasicWindowCovering */
}
