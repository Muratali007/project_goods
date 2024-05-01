package com.example.task.controller;

import static com.example.task.constants.Constants.SUCCESS_MESSAGE;

import com.example.task.data.entity.Goods;
import com.example.task.service.GoodsService;
import jakarta.ejb.EJB;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/goods")
public class GoodsController {
  @EJB
  private GoodsService goodsService;

  @POST
  @Path("/save")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response saveGoods(Goods goods) {
    try {
      goodsService.saveGoods(goods);
      return Response.ok(SUCCESS_MESSAGE).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @POST
  @Path("/update")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateGoods(Goods goods) {
    try {
      goodsService.updateGoods(goods);
      return Response.ok(SUCCESS_MESSAGE).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Path("/get/all")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllGoods() {
    try {
      List<Goods> goodsList = goodsService.getAllGoods();
      return Response.ok(goodsList).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Path("/get/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getGoodsById(@PathParam("id") Long id) {
    try {
      Goods goods = goodsService.getGoodsById(id);
      return Response.ok(goods).build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
}
