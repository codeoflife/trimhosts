When maintaining a list of hosts (to block, adblock etc) with updates by various sources, it gets hard to check that there are no duplicates.

This little utility removes duplicates and sorts by hostname.
Simple combine the two text files and use this to ensure no duplicates.

Note: Comments are removed too, so backup your old file and add those in manually.

**To create the jar**
jar -cvfm trimhosts.jar MANIFEST.MF com

**Example usage**
java -jar trimhosts.jar C:\Users\myname\Desktop\hosts

For windows you can find the hosts file at: %systemroot%\system32\drivers\etc\hosts and for unix its at /etc/hosts