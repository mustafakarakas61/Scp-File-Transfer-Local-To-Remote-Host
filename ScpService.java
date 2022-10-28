package com.mustafa.service;                                                     //Package Name

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScpService {

    public boolean fileTransferLocalToRemote(String fileName) throws JSchException, SftpException {
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession("username", "remoteHostIp", 22);  //username, remoteHostIp, Port 22 for SFTP
        jschSession.setConfig("StrictHostKeyChecking", "no");
        jschSession.setPassword("remoteHostPassword");                          //remoteHostPassword
        jschSession.setConfig("PreferredAuthentications", "password");
        jschSession.connect();
        Channel sftp = jschSession.openChannel("sftp");

        sftp.connect();
        ChannelSftp channelSftp = (ChannelSftp) sftp;
        channelSftp.put("localPath/" + fileName, "remotePath");                 //"localPath/fileName", "remotePath"

        channelSftp.disconnect();
        jschSession.disconnect();

        if(!jschSession.isConnected())
            return true;
        else
            return false;
    }
}
