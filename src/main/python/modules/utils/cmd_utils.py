import subprocess

from utils.logging_configs import *

logger = logging.getLogger('monitor_stats')


def run_cmd(command, args=None):
    """
    
    :param command: 
    :param args: 
    :return: 
    """
    if not command:
        return None
    if args and type(args) is not list:
        raise ValueError("Args must be of type list()")

    arguments = " ".join(str(x) for x in args)

    ret = (-1, None)
    try:
        logger.info("Firing command : '" + command + ' ' + arguments + "'")
        proc = subprocess.Popen([command + ' ' + arguments], stdout=subprocess.PIPE, shell=True)
        (out, err) = proc.communicate()
        logger.debug("Command returned with " + str(out))
        ret = out
    except Exception as e:
        logger.error("Command failed to execute\n" + str(e))

    return ret
