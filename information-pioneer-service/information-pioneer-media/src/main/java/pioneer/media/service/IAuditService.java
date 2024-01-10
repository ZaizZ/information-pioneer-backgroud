package pioneer.media.service;


import pioneer.media.entity.WmNews;

public interface IAuditService {

    public void audit(WmNews news);
    public void audit(Integer id);
}
