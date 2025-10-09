package org.example.springhemingproject02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.dox.Node;
import org.example.springhemingproject02.dto.NodeBig;
import org.example.springhemingproject02.repository.NodeClosureRepository;
import org.example.springhemingproject02.repository.NodeRepository;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NodeService {
    private  final NodeClosureRepository nodeClosureRepository;
    private final NodeRepository nodeRepository;
    @Transactional
    public ResultVO addNode(Node node){
        nodeRepository.save(node);
        nodeClosureRepository.addNodeClosure(node.getParentId(),node.getId());
        return ResultVO.ok();
    }
    @Transactional
    public ResultVO getNodes(@Param("id") String id){
        List<NodeBig> nodebigs = nodeRepository.findNodeBigsById(id);
        return ResultVO.builder().data(Map.of("nodebigs",nodebigs)).build();
    }

    public ResultVO deleteNode(String id) {
        nodeClosureRepository.deleteNodeRelations(id);
        nodeRepository.deleteNode(id);
        return ResultVO.ok();
    }

    public Optional<Node> findById(String id) {
        return nodeRepository.findById(id);
    }

    public ResultVO getfirstNodes() {
        List<Node> nodes = nodeRepository.findFirstNodes();
        return ResultVO.builder().data(Map.of("nodes",nodes)).build();
    }

}
